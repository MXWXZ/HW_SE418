# 上海交大网站分析
## 概要
- 网址：https://www.sjtu.edu.cn/
- IPV4：`202.120.2.119`
- IPV6：`2001:da8:8000:1::2:119`
- HTTPS：强制
- HTTP/2.0：不支持
- gzip：支持
- 技术栈（部分源自 Wappalyzer)：
  - Widget
    - OWL Carousel
  - Web Framework
    - animate.css
    - Bootstrap3.3.7
    - ETUI3（搜不到相关信息，可能为外包公司自研）
  - JavaScript Libraries
    - jQuery1.12.4
  - Server
    - apache

## 网页加载
- 错误信息：无警告无错误
- 加载时长（内网）：2040 ms
- 在0.135s-5.54s的监测时间内：
  
  1. Loading: 125.2 ms
  2. Scripting: 2146.9 ms
  3. Rendering: 760.6 ms
  4. Painting: 209.1 ms
  5. Other: 531.3 ms
  6. Idle: 1628.9 ms
  
  可以看出大量使用的Jquery等脚本运行占据了大部分加载时间（取决于客户端配置），但是对服务器端压力较小，可以容纳更多的并发数。

## 同类对比
复旦大学：http://www.fudan.edu.cn/mindex.html
- HTTPS：不支持
- 加载速度：480 ms
- Loading: 38.6 ms
- Scripting: 235.1 ms
- Rendering: 100.8 ms
- Painting: 18.0 ms

清华大学：http://www.tsinghua.edu.cn/publish/thu2018/index.html
- HTTPS：支持，默认不开启
- 加载速度：3000 ms (lazyload, 显示时间515 ms)
- Loading: 51.4 ms
- Scripting: 332.2 ms
- Rendering: 230.3 ms
- Painting: 80.6 ms

结论：
- 过多的JS特效导致脚本运行时间很长，同比10倍的差距。
- 安全性方面同比较为优秀，强制使用https。

## 网站优化
- 全站静态化
- `headersy.js`，使用JS脚本动态加入院系设置，提高用户体验。推测该文件缓存了数据库内容，减轻查询负担。
- 大量使用jquery及其插件，提高美观度。
- 使用Bootstrap框架，自适应布局，移动端适配较好。

## SEO问题
- `robots.txt`不存在
- 网站图片没有ALT信息
- 网站`KeyWords`和`Description`丢失

## 其他建议
- 网站静态资源建议分离，方便做CDN等加速。
- 图片资源建议使用TinyPNG等工具压缩，可以加快访问速度。例如庙门背景经过压缩图片体积从123.3 KB下降到92.3 KB，减少了25%.
- 减少不必要的特效，提高JS执行效率。