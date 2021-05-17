# 景院博客社区

#### 介绍

景院博客网站属于社区型博客。面向校内师生，用博客来分享自己新颖的想法、记录自己生活与经历。提供一个让展示自己技能，创造力和才能的平台。与校内志同道合的校友进行互动等。同时可以在博客社区里面发布校园新闻、活动，提高新闻、活动传播范围。通过对博客系统的建设熟悉对Java Web的使用；锻炼对前端Semantic UI与Spring Boot等框架的运用；熟悉数据库的设计。培养独立思考，深入钻研，分析、解决问题的能力。通过实际的网站项目的设计分析、开发建设，掌握网页前后端的分析方法和工程设计方法。
博客系统所需的功能,主要是管理所有用户发表的文章、留言、系统资料等。而且数据的种类直接决定着应用程序针对数据进行处理的方式即算法,因此,数据分析是整个系统设计和实现数据分析的一个出发点。现通过一种基于数据流图的形式来分析整个系统中各种数据流动及其处理。而对于博客网站的管理员，在经过管理员登录之后,网站就会保留登录信息，从后端接收管理员的增删改查等操作,通过后端的将这一系列操作存储至数据库，并且系统将对应这些操作作出反应。系统从数据库中获取所需信息，经过前后端的处理后，最终将其转化为一个个页面展示在网站中，供用户使用。

### 技术栈

#### 后端
-  Spring Boot
-  Spring Data JPA
-  MySql

#### 前端
-  [Thymeleaf](https://www.thymeleaf.org/)
-  [Semantic UI](https://semantic-ui.com/introduction/getting-started.html)
-  [Bootstrap](https://v3.bootcss.com/)

#### 通用
- 原生语言：java、js、css、html5
- 开源库：[jquery.js](https://www.bootcdn.cn/jquery/)、[prism.js](https://www.bootcdn.cn/prism/)、[editormd.js](https://github.com/pandao/editor.md)、[animate.css](https://www.bootcdn.cn/animate.css/)、[FileUtils.java](https://github.com/coltoscosmin/FileUtils/blob/master/FileUtils.java)


### 安装教程
1. - 通过maven自动下载依赖，下载完成后修改Spring Boot配置文件中的数据库配置为自己的数据库。
1. - 在数据库中新建一个名为jyblog的数据库，将sql文件夹中的jysql.sql导入数据库中。

#### 使用说明
- 推荐使用IDEA运行
