"use strict"
let usage = () => "Available commands:\n  clean\n  list-pieces\n  playground\n  deploy <piece>|index|everything"

let fs = require('fs')
let path = require('path')
let child_process = require('child_process')
let mkdirp = require('mkdirp').sync
let jinjs = require('jinjs')

let cfg = {}
cfg.blog_title = "electronic schmart"
cfg.blog_description = "bits and pieces by Naji Mammeri"
cfg.blog_url = "http://pascience.net/a"
cfg.author_email = "naji@pascience.net"
cfg.author_fullname = "Naji Mammeri"
cfg.rss_ttl_in_seconds = 60*60*24*5
cfg.guid_prefix = "nj-schmart-"
cfg.basedir = fs.realpathSync('.')
cfg.build_dir = path.join(cfg.basedir, "build")
cfg.build_dir_www = path.join(cfg.build_dir, "www")
cfg.build_dir_js = path.join(cfg.build_dir_www, "js")
cfg.src_dir = path.join(cfg.basedir, "src")
cfg.clojure_cmd = "rlwrap lein run -m clojure.main build.clj"

let exit = () => {
	process.exit(1)
}

let print_usage_and_exit = () => {
	console.log(usage())
	exit()
}

let copyFileSync = (src, dest) => {
  fs.writeFileSync(dest, fs.readFileSync(src))
}

let clean = () => {
	exec(`rm -rf ${cfg.build_dir} *-init.clj figwheel_server.log`)
}

let mkdirp_everything = () => {
	mkdirp(cfg.build_dir_www)
	mkdirp(cfg.build_dir_js)
}

let find_all_pieces = () => {
	return fs.readFileSync(path.join(cfg.src_dir, 'index.txt')).toString()
	.split('\n')
	.reduce((pieces, line) => {
		line = line.trim()
		if(line == "") {
			return pieces
		}
		let [pubdate, id, title, tags] = line.split(' ## ')
		return pieces.concat([{
			id, pubdate, title, tags: tags.split(',').map(x => x.trim())
		}])
	}, [])
}

let find_piece = (piece_id) => {
	for(let p of find_all_pieces()) {
		if(p.id == piece_id) {
			return p
		}
	}
	return null
}

let njdate_to_jsdate = (date) => new Date(date)

let njdate_to_rssdate = (date) => {
	return njdate_to_jsdate(date).toUTCString()
}

let piece_error = (p) => `The piece '${p}' doesn't exist or isn't listed in src/index.txt.`

let render_with_template = (template_string, obj) => {
	return jinjs.defaultEnvironment.getTemplateFromString(template_string).render(obj)
}

let copy_css = () => {
	copyFileSync(path.join(cfg.src_dir, "reset.css"), path.join(cfg.build_dir_www, "reset.css"))
	copyFileSync(path.join(cfg.src_dir, "schmart.css"), path.join(cfg.build_dir_www, "schmart.css"))
}

let deploy_pieces = (piece_id) => {
	let piece = find_piece(piece_id)
	if(piece == null) {
		console.log(piece_error(piece_id))
		exit()
	}
	console.log(`Deploying piece ${piece_id}...`)
	let piece_tpl = fs.readFileSync(path.join(cfg.src_dir, "piece.html.tpl")).toString()
	let obj = cfg
	obj.piece = piece
	let html = render_with_template(piece_tpl, obj)
	fs.writeFileSync(path.join(cfg.build_dir_www, `${piece.id}.html`), html)
	exec(cfg.clojure_cmd, { CLJS_BUILD: "production", CLJS_BUILD_DIR: cfg.build_dir_js, CLJS_PIECE: ''+piece_id })
}

let deploy_index_and_rss = () => {
	let pieces = find_all_pieces()

	let index_tpl = fs.readFileSync(path.join(cfg.src_dir, "index.html.tpl")).toString()
	let index_cfg = cfg
	index_cfg.pieces = pieces
	let index_html = render_with_template(index_tpl, index_cfg)

	let rss_tpl = fs.readFileSync(path.join(cfg.src_dir, "rss.xml.tpl")).toString()
	let rss_cfg = cfg
	rss_cfg.pieces = pieces.map(x => Object.assign({}, x, {
		pubdate: njdate_to_rssdate(x.pubdate),
		guid: `${cfg.guid_prefix}${x.id}`
	}))
	rss_cfg.pubdate = pieces.reduce((oldest, { pubdate }) => {
		let d = njdate_to_jsdate(pubdate)
		return (d < oldest) ? d : oldest
	}, (new Date())).toUTCString()
	rss_cfg.last_build_date = (new Date()).toUTCString()
	let rss_xml = render_with_template(rss_tpl, rss_cfg)

	fs.writeFileSync(path.join(cfg.build_dir_www, "index.html"), index_html)
	fs.writeFileSync(path.join(cfg.build_dir_www, "rss.xml"), rss_xml)
}

let exec = (cmd, env) => {
	if(env == null) {
		env = {}
	}
	console.log(cmd)
	child_process.execSync(cmd, { stdio: 'inherit', env: Object.assign({}, process.env, env)})
}

let run = (args) => {
	let firstarg, what
	try {
		firstarg = args[0]
		what = args[1]
	} catch(e) {
		print_usage_and_exit()
	}

	mkdirp_everything()

	if(firstarg == "playground") {
		exec(cfg.clojure_cmd, { CLJS_BUILD: "playground", CLJS_BUILD_DIR: cfg.build_dir_js })
	} else if(firstarg == "list-pieces") {
		for(let p of find_all_pieces()) {
			console.log(`${p.id} (${p.pubdate}): ${p.title} ## ${p.tags}`)
		}
	} else if (firstarg == "deploy") {
		if(what == null) {
			print_usage_and_exit()
		}

		copy_css()
		if(what == "everything") {
			for(let p of find_all_pieces()) {
				deploy_piece(p.id)
			}
			deploy_index_and_rss()	
		} else if(what == "index") {
			deploy_index_and_rss()
		} else {
			let piece = find_piece(what)
			if(piece == null) {
				console.log(piece_error(what))
				exit()	
			}
			deploy_piece(piece.id)	
		}
	} else if (firstarg == "clean") {
		clean()
	} else {
		print_usage_and_exit()
	}
}

run(process.argv.slice(2))
