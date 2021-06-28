const fs = require("fs");
const nodeCommon = require("../common/nodeCommon");
const webpackFile = require("./webpack.file.conf");
const entryBuild = require('../entry/entry');
const webpackComConf = require('./webpack.com.conf');
/*删除构建目录*/
nodeCommon.deleteFile(webpackFile.devDirectory);
/*创建构建目录*/
fs.mkdirSync(webpackFile.devDirectory);
fs.mkdirSync(webpackFile.devDirectory + '/html');
webpackFile.copyObj.map((data) => {
    let to = webpackFile.devDirectory + data.to.substring(1, data.to.length);
    if (data.to !== './') {
        fs.mkdirSync(to);
    }
    nodeCommon.copyFile(data.from, to);
});
/*生成HTML*/
let htmlCont = fs.readFileSync("index.html", "utf-8");
let scriptInsertHead = `
 <script type="text/javascript" src="../venderjs/jquery.min.js"></script>
 <script type="text/javascript" src="../venderjs/bootstrap.min.js"></script>
 <script type="text/javascript" src="../venderjs/bootbox.min.js"></script>
 <script type="text/javascript" src="../venderjs/bootstrapValidator.min.js"></script>
`;
let scriptInsertBody = `
 <script type="text/javascript" src="js/key1.js"></script>
 <script type="text/javascript" src="js/key2.js"></script>
`
htmlCont = htmlCont.replace('</head>', scriptInsertHead + '</head>');
htmlCont = htmlCont.replace('</body>', scriptInsertBody + '</body>');
entryBuild.map((data) => {
    fs.writeFile(webpackFile.devDirectory+ '/html/' + data.name + '.html',
        htmlCont.replace('js/key1.js', '../venderjs/domain.js').replace('js/key2.js', '../js/' + data.name + '.bundle.js').replace('<%= htmlWebpackPlugin.options.title %>', webpackComConf.titleFun(data.name,data.title)),
        'utf8',
        function (err) {
            if (err) {
                return console.log(err);
            }
        });
});
