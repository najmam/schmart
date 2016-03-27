<!DOCTYPE html><html><head><meta charset="utf-8">
<title>{{blog_title}}</title>
<link rel="alternate" type="application/rss+xml" href="rss.xml" title="{{blog_title}}">
<link rel="stylesheet" href="/reset.css">
<link rel="stylesheet" href="/pascience.css">
</head>
<body class="schmart-index">
	<div class="root">
		<h1 class="title">{{blog_title}}</h1>
		<p class="subtitle">{{blog_description}}</p>
		
		<ul class="main">
		{% for piece in pieces %}
		<li><span class="pubdate">{{piece.pubdate}}</span> <a href="{{piece.id}}.html">{{piece.title}}</a></li>
		{% endfor %}
		</ul>
	</div>
</body>
</html>
