var gulp = require('gulp');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var watch = require('gulp-watch');
const babel = require('gulp-babel');


//type gulp in terminal to 'gulp' everything
gulp.task('default', ['html', 'css', 'js']);


//html
gulp.task('html', function() {
    gulp.src('index.html')
        .pipe(gulp.dest('./public'))
});


// css
gulp.task('css', function() {
    gulp.src('main.css')
        .pipe(gulp.dest('./public'));
});

// js
gulp.task('js', function() {
    gulp.src(['apps.js', 'templates.js'])
        // .pipe(concat('all.js'))
        .pipe(babel({
            presets: ['es2015']
        }))
        .pipe(uglify({
          mangle: true,
        }))
        .pipe(gulp.dest('./public'));
});

//watch
gulp.task('watch', function() {
    gulp.watch('./index.html', ['html']);
    gulp.watch('./js/apps.js', ['js']);
    gulp.watch('./js/templates.js', ['js']);
    gulp.watch('./main.css', ['css']);
})
