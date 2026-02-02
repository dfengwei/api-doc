# api-doc
![JAVA](https://img.shields.io/badge/java-8+-blue) ![License](https://img.shields.io/badge/license-MIT-green) ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.dfengwei/api-doc)

> 基于java注释的接口文档生成器 

## 主要特性

- **低配置**：尽量减少非必要的配置项，尽量使用自解释的方式生成接口文档。
- **容错**：检查并报告配置文件、接口注释中的错误，有一定的容错能力。
- **自定义内容**：通过引用Markdown文件，将Markdown转换为HTML格式，可以自定义接口文档中的部分内容。
- **树形导航**：灵活紧凑的树形导航，在保证层次清晰的前提下，尽量紧凑地列出接口。
- **区域区分**：每个引用的Markdown文件、每个接口，按块状分隔显示，当前块高亮显示。
- **多栏显示**：内容块自适应多栏显示，充分利用高分辨率显示器的优势。
- **色彩区分**：请求头、请求参数、成功返回、失败返回等区域分颜色显示，有利于视觉快速区分，降低认识负荷，减少混淆风险。
- **使用方便**：使用java项目自带的JDK即可完成文档的生成，不依赖于其他运行环境，不将无关jar包带入运行环境，支持Jenkins等持续集成工具。

## 术语
- **终端**：一个工程可以为多种形式的终端提供接口，例如：浏览器（PC）、客户端（PC）、APP、微信小程序等。每种终端接口的身份认证方式、返回码等可以不同，每种终端还可以有自己的一些特定的规则。
- **文档级Markdown引用**：整个工程级别的说明文档，比如概述、版本、统一说明、返回码等。引用分为头部引用和底部引用。
- **终端级Markdown引用**：某个终端特有的说明文档，比如某个终端特有的返回码，特有的接口调用公共参数等。

## 配置文件
```javascript
{
  // 文档名称（生成的HTML文件的title、Markdown文件的文件名）
  // 选填，默认值：<工程根目录名>
  "docName": "xxx项目接口文档",
  // HTML接口文档渲染器配置
  // 选填，默认会渲染HTML接口文档
  "htmlRenderer": {
    // 是否启用HTML接口文档渲染
    // 选填，默认值：true
    "enable": true,
    // 是否使用单文件模式，true：输出单个HTML文件（包含了各个静态资源）；false：输出一个HTML文件，并输出其引用的资源文件
    // 选填，默认值：false（默认多文件模式，能有效利用浏览器的并发请求特性，在一定程度上提升页面的加载速度）
    "singleFile": false,
    // 是否压缩（对HTML内容进行简单的压缩，减小HTML文件的尺寸）
    // 选填，默认值：true
    "compress": true,
    // 输出路径（相对于工程根目录）
    // 选填，默认值：out/api-doc/html
    "path": "out/api-doc/html"
  },
  // Markdown接口文档渲染器配置
  // 选填，默认不渲染Markdown接口文档
  "markdownRenderer": {
    // 是否启用Markdown接口文档渲染
    // 选填，默认值：false
    "enable": false,
    // 输出路径（相对于工程根目录）
    // 选填，默认值：out/api-doc/markdown
    "path": "out/api-doc/markdown"
  },
  // Markdown文件引用列表
  // 选填
  "markdownList": [
    {
      // 名称（菜单项名称）
      // 选填，默认值：<引用的Markdown文件的文件名>
      "name": "全局概述",
      // 类型，HEADER：头部；FOOTER：底部
      // 选填，默认值：HEADER
      "type": "HEADER",
      // 文件路径（相对于工程根目录）
      // 必填
      "filePath": "src/main/resources/.../xxx.md"
    }
  ],
  // 终端列表
  // 必填
  "terminalList": [
    {
      // 终端名称
      // 选填，默认值：<扫描路径的最后一个目录名>
      "name": "APP端",
      // 接口扫描路径（接口源码文件所在路径）（相对于工程根目录）
      // 必填
      "scanPath": "src/main/java/.../controller/app",
      // 终端头部Markdown文件列表
      // 选填
      "markdownList": [
        {
          // 名称（菜单项名称）
          // 选填，默认值：引用的Markdown文件的文件名
          "name": "APP端-概述",
          // 类型，HEADER：头部；FOOTER：底部
          // 选填，默认值：HEADER
          "type": "FOOTER",
          // 文件路径（相对于工程根目录）
          "filePath": "src/main/resources/.../app/xxx.md"
        }
      ]
    }
  ]
}
```

## 文档编写格式

- **@apiDisable**：禁用本接口
  - 非必填，如果存在此注释，则此接口不会出现在接口文档中
- **@apiName**：接口名称
  - 必填，格式为：{一级分组序号:一级分组名称}  {二级分组序号:二级分组名称}  {三级分组序号:三级分组名称} ... {接口序号:接口名称}
  - 分组为树状结构，分组序号为同一父分组下的子分组的排序序号。多个接口声明的同一分组，如果序号不同，则使用首次扫描到的分组的序号，并进行info级日志提示。
  - 一个接口至少拥有一个分组。
- **@apiDescription**：接口描述
  - 非必填，文本形式，用于对接口细节及注意事项的说明。
- **@apiAddr**：接口地址
  - 必填，格式为 {请求方式1} {请求方式2} ... 请求url
  - 请求方式为GET、POST等字符串，若为空，将默认同时支持GET和POST，并进行info级日志提示。
  - 请求url为常见的url格式，若为空，将进行warn级日志提示。
- **@apiHeader**：请求头
  - 非必填，格式：{数据类型} 字段名 字段描述。
  - 数据类型：String、VARCHAR(20)、JSONObject等能说明字段类型、长度等信息的字符串。
  - 字段名：
    - xxx：必填字段，HTML页面中带实线下划线。
    - [xxx]：表示非必填字段，HTML页面中带虚线下划线。
    - xxx.yyy.zzz：树形必填字段，只表示zzz是必填，不代表xxx和yyy也是必填。
    - [xxx.yyy.zzz]：树形非必填字段，只表示zzz是非必填，不代表xxx和yyy也是非必填。
- **@apiParam**：请求参数
  - 非必填，格式：{数据类型} 参数名 参数描述。
  - 数据类型：参考`@apiHeader`
  - 参数名：参考`@apiHeader`
- **@apiSuccess**：成功返回字段
  - 非必填，格式：{数据类型} 字段名 字段描述
  - 数据类型：参考`@apiHeader`
  - 字段名：参考`@apiHeader`
- **@apiFail**：成功返回字段
  - 非必填，格式：{数据类型} 字段名 字段描述
  - 数据类型：参考`@apiHeader`
  - 字段名：参考`@apiHeader`

### 文档编写示例

```java
/**
 * @apiDisable
 * @apiName {1:业务} {2:产品} 1:列表
 *
 * @apiDescription 获取产品列表
 *
 * @apiAddr {GET} {POST} /xxx/business/product/list
 *
 * @apiHeader {String} token 登录用户的Token
 *
 * @apiParam {String} [id] 产品id
 * @apiParam {String} [productName] 产品名称
 *
 * @apiSuccess {String} code 返回码, 成功固定返回：00000
 * @apiSuccess {JSONObject} data 返回的数据
 * @apiSuccess {JSONArray} data.list 产品列表
 * @apiSuccess {char(21)} data.list.id 产品id
 * @apiSuccess {varchar(50)} data.list.productName 产品名称
 * @apiSuccess {varchar(2000)} [data.list.productDescription] 产品描述
 * @apiSuccess {JSONArray} [data.list.categoryList] 产品分类对象列表
 * @apiSuccess {String} data.list.categoryList.code 产品分类编号
 * @apiSuccess {String} data.list.categoryList.name 产品分类名称
 *
 * @apiFail {String} code 返回码
 * @apiFail {String} [msg] 提示信息
 * @apiFail {String} [error] 错误信息
 */
@RequestMapping("/xxx/system/user/list")
public ModelAndView list(HttpServletRequest request) {

}
```

## 文档生成方式

### gradle
- 使用Maven仓库：

```groovy
// 使用Maven中央仓库或其他镜像仓库
repositories {
    mavenCentral()
}

// 添加编译期依赖（无需将依赖带入运行环境）
dependencies {
    compileOnly group: 'io.github.dfengwei', name: 'api-doc', version: 'x.y.z'
}

// 添加JavaExec任务
task doc(type: JavaExec) {
    // api-doc的入口
    mainClass = "com.github.dfengwei.apidoc.Generator"
    // 调用参数
    // 1. 配置文件路径（基于项目根目录的相对路径），默认为：src/main/resources/api-doc.json
    // 2. 日志级别，包括：TRACE、DEBUG、INFO、WARN、ERROR，默认为：INFO
    args 'src/main/resources/api-doc.json', 'INFO'
    // api-doc jar包路径（编译环境）
    classpath = sourceSets.main.compileClasspath
}
```
- 直接使用jar包：

```groovy
// 假设已将api-doc-x.y.z.jar放置于项目根目录的libs目录下

// 修改依赖为：
compileOnly files('libs/api-doc-x.y.z.jar')
```

- 文档生成命令：

```shell
gradlew doc
```

## 从源码构建

```cmd
git clone https://github.com/dfengwei/api-doc.git
cd api-doc
gradlew build

# api-doc.jar位于build/libs中
```

## maven

略（本人不太使用maven）
