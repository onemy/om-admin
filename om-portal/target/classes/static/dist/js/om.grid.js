/**
 * 将表格转换为 dataTables 控件
 */
$(document).ready(function() {
	/**
	 * 绘制表格
	 */
	$('table[data-view=dataTables]').each(function(index, node){
		if (!$.fn.DataTable.isDataTable (this)) {
			//防止重复初始化 dataTables
			$(this).wrap ('<div class="row"><div class="col-sm-12"></div></div>');
			$(this).vTable();
		}
	});
	
	/**
	 * 注册行点击事件
	 */
    $('table[data-view=dataTables] tbody').on('click', 'tr', function () {
        //$(this).toggleClass('selected');
        
        var checkbox = $(this).find(':checkbox[name="ids"]');
        checkbox.prop('checked', !checkbox.prop('checked'));
    });	
    /**
     * 全选功能
     * @returns
     */
    $(':checkbox[name="all"]').click(function () {
    	$(':checkbox[name="ids"]').prop('checked',$('#all').prop('checked'));
    });
});