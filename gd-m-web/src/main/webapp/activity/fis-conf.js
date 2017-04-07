// default settings. fis3 release

// Global start
var pUrl = '/activity/dist';
fis.match('*.{js,css}', {
  useHash: true
});

fis.match('::image', {
  useHash: true
});

// 启用 fis-spriter-csssprites 插件
fis.match('::package', {
  spriter: fis.plugin('csssprites')
})

// 对 CSS 进行图片合并
fis.match('*.css', {
  // 给匹配到的文件分配属性 `useSprite`
  useSprite: true
});

fis.match('*.js', {
  //js打包
  //packTo: '/static/index.js',
  // fis-optimizer-uglify-js 插件进行压缩，已内置
  optimizer: fis.plugin('uglify-js')
});

fis.match('*.css', {
  // fis-optimizer-clean-css 插件进行压缩，已内置
  optimizer: fis.plugin('clean-css')
});

fis.match('*.png', {
  // fis-optimizer-png-compressor 插件进行压缩，已内置
  optimizer: fis.plugin('png-compressor')
});

fis.match('*.{js,css,png,jpg,gif,mp3}', {
	release: '/static$0',
	url : pUrl + '/static$0'
});

//fis.match('/js/comment/*.js', {
//packTo: '/static/js/comment_pkg.js',
//url : pUrl + '/static/js/comment_pkg.js'
//})

//打包
//npm install -g fis3-postpackager-loader
fis.match('::package', {
  postpackager: fis.plugin('loader', {
    allInOne: true
  })
});

// You need install it.
// npm i fis-optimizer-html-minifier [-g]
//
fis.match('*.html', {
  //invoke fis-optimizer-html-minifier
  optimizer: fis.plugin('html-minifier')
});

fis.media('debug').match('*.{js,css,png}', {
  useHash: false,
  useSprite: false,
  optimizer: null
});

// Global end

// default media is `dev`
fis.media('dev')
  .match('*', {
    //useHash: true
    //optimizer: null
  });

// extends GLOBAL config
fis.media('production');