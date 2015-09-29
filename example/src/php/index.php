<html>
  <head profile="http://www.w3.org/2005/10/profile">
    <meta charset="utf-8">
    <meta http-equiv="Content-type" content="text/html;charset=utf-8">
    <meta name="viewport" content="initial-scale=1,width=device-width">
    <?php $is_minimized_build = getenv('BUILD_TYPE') == 'minimized'; ?>
    <title>Hello World in ClojureScript/React</title>
    <style>
      body {
        margin: 0;
        -webkit-font-smoothing: antialiased;
        -moz-font-smoothing: antialiased;
        -o-font-smoothing: antialiased;
      }
    </style>
    <?php if (!$is_minimized_build) { ?>
      <script src="build/goog/base.js"></script>
    <?php } ?>
  </head>
  <body>
    <div id="appContainer"></div>
    <script src="compiled.js"></script>
    <?php if (!$is_minimized_build) { ?>
      <script>goog.require('dmohs.main');</script>
    <?php } ?>
    <script>
      var app = dmohs.main.render(document.getElementById('appContainer'));
    </script>
  </body>
</html>
