<!DOCTYPE html><html><head><meta charset="utf-8">
<title>{{piece.title}} | {{blog_title}}</title>
<meta property="og:title" content="{{piece.title}}">
<meta property="og:description" content="{{piece.description}}">
<meta property="og:image" content="{{blog_url}}/{{piece.id}}.png">

<meta property="og:site_name" content="{{blog_title}}">
<link rel="alternate" type="application/rss+xml" href="rss.xml" title="{{blog_title}}">

<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
<link rel="stylesheet" href="/reset.css">
<link rel="stylesheet" href="schmart.css">
</head>
<body class="sketch sk{{piece.id}}">
  <div class="root">
    <canvas id="sketch"></canvas>
  </div>
  <script src="js/sk{{piece.id}}.js"></script>
  <!-- {{ piece.pubdate }} -->
</body>
</html>
