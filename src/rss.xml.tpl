<?xml version="1.0" encoding="UTF-8" ?>
<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
<channel>
  <atom:link href="{{blog_url}}/rss.xml" rel="self" type="application/rss+xml" />
  <title>{{blog_title}}</title>
  <description>{{blog_description}}</description>
  <webMaster>{{author_email}} ({{author_fullname}})</webMaster>
  <link>{{blog_url}}</link>
  <ttl>{{rss_ttl_in_seconds}}</ttl>
  <pubDate>{{pubdate}}</pubDate>
  <lastBuildDate>{{last_build_date}}</lastBuildDate>

  {% for piece in pieces %}
  <item>
    <title>{{piece.title}}</title>
    <description>{{piece.description}}</description>
    <link>{{blog_url}}/{{piece.id}}.html</link>
    <guid isPermaLink="false">{{piece.guid}}</guid>
    <pubDate>{{piece.pubdate}}</pubDate>
  </item>
  {% endfor %}
</channel>
</rss>
