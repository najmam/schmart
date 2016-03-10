<!DOCTYPE html><html><head><meta charset="utf-8">
<title>{{blog_title}}</title>
<link rel="alternate" type="application/rss+xml" href="rss.xml" title="{{blog_title}}">
<link rel="stylesheet" href="reset.css">
<link rel="stylesheet" href="schmart.css">
</head>
<body class="index">
	<div class="root">
		<h1>{{blog_title}}</h1>
		<p class="subtitle">{{blog_description}}</p>
		
		<ul>
		{% for piece in pieces %}
		<li>{{piece.pubdate}}: <a href="{{piece.id}}.html">{{piece.title}}</a></li>
		{% endfor %}
		</ul>
	</div>
</body>
</html>
