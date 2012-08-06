/**
 * Allows $(function() {}); to be used even without Jquery loaded - This code fragments runs the stored calls.
 * This is useful so we can push all the $(function() { ...}); calls in templates without having to load jquery at head
 */
window.$.noConflict();window.$ = window.$.attachReady(jQuery);

/** CUSTOM SCRIPTS HERE */
/**
* Sets the proper selected option in the navbar
*/
function setNavbar(id) {
    //remove all selectors
    $('#menuHome').removeClass('active');
    $('#menuPlay1').removeClass('active');
    $('#menuPlay2').removeClass('active');

    //set given one
    $("#menu"+id).addClass('active');
}

/** END OF CUSTOM SCRIPTS */

/**
 * Prevention of window hijack, run after all jquery scripts
 */
$('html').css('display', 'none');
if( self == top ) {
    document.documentElement.style.display = 'block';
} else {
    top.location = self.location;
}


/* Google analytics script, run at the end - Change UA-XXXXX-X to be your site's ID */
//var googleAnalyticsCode = 'UA-XXXXX-X';
//window._gaq = [['_setAccount', googleAnalyticsCode],['_trackPageview'],['_trackPageLoadTime']];
//Modernizr.load({load: (if 'https:' == location.protocol then '//ssl' else '//www') + '.google-analytics.com/ga.js'});