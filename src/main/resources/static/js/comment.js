/**
 * コメントに関する処理を行うjavascript
 */
 $(function(){
    $('#greet').click(function(){
    	if(!$('#user').val() || !$('#message').val()) return;
        $.get('bbs.php', {
        	user: $('#user').val(),
        	message: $('#message').val(),
        	mode: "0" //書き込み
        }, function(data){
        	$('#result').html(data);
        	scTarget();
        });
    });
    loadLog();
    logAll();
});

// コメントのログをロード
function loadLog(){
	$.get('bbs.php', {
		mode: "1" // 読み込み
    }, function(data){
    	$('#result').html(data);
    	// scTarget();
    });
}

// 定期的なログを取得する
function logAll(){
	setTimeout(function(){
		loadLog();
		logAll();
	},5000); //リロード時間
}