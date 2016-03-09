all: rlwrap-figwheel

nothing:
	@echo "Default task - nothing to do."

rlwrap-figwheel:
	rlwrap lein run -m clojure.main figwheel.clj dev
figwheel:
	lein run -m clojure.main figwheel.clj dev
devcards:
	lein run -m clojure.main figwheel.clj devcards

once:
	lein cljsbuild once dev

css:
	gulp less

clean:
	find . -name \*init.clj | xargs rm -f
	rm -f figwheel_server.log
