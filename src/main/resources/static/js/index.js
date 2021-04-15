layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var laydate = layui.laydate,
        table = layui.table,
        form = layui.form,
        $ = layui.$;

    laydate.render({
        elem: '#time',
        type: 'datetime'
    });

    table.render({
        elem: "#article_table",
        id: 'tableList',
        even: true, //开启隔行背景
        url: '/article/articleList',
        request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        },
        title: '文章列表',
        page: true,
        limit: 10,
        limits: [1, 5, 10, 20, 50, 100],
        cols: [[
            {checkbox: true, align: "center", LAY_CHECKED: false},
            {title: '序号', width: 60, align: 'center', type: 'numbers'},
            {
                file: 'type', title: '分类', width: '10%', align: 'center', templet: function (i) {
                    if (i.type == 1) {
                        return 'Java';
                    } else if (i.type == 2) {
                        return 'Linux';
                    } else if (i.type == 3) {
                        return '数据库';
                    } else if (i.type == 4) {
                        return '笔记';
                    } else {
                        return '未分类';
                    }
                }
            },
            {field: 'title', title: '标题', width: '15%', align: 'center'},
            {field: 'author', title: '作者', width: '9%', align: 'center'},
            {field: 'content', title: '内容', align: 'center'},
            {field: 'createTime', title: '创建时间', width: '15%', align: 'center'},
            {
                fixed: 'right',
                title: '操作',
                toolbar: '#article_lineBar',
                width: 120,
                align: 'center'
            }
        ]]
    });

    $("#refresh").click(function () {
        //即时刷新
        table.reload('tableList', {
            where: '',
            contentType: 'application/x-www-form-urlencoded',
            page: {
                curr: 1
            },
            url: '/article/articleList',
            method: 'get'
        });
    });

    $("#add").click(function () {
        var data = {};
        data.action = 'addArticle';
        data.request_type = 'post';

        //如果在点击新增按钮之前先执行了"edit"事件，则会把form表单中的内容带过来，此处需做清空
        $('#add_form')[0].reset();
        form.render();

        //调用打开弹层的工具方法
        open_form("#open_div", data, '添加文章', '680px', '282px');
    });

    //批量删除
    active = {
        getCheckData: function () {
            var checkStatus = table.checkStatus('tableList'),
                data = checkStatus.data;

            if (data == "") {
                layer.msg("请选择要删除的数据", {icon: 2, time: 2000});
                return;
            }
            var ids = [];
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    ids.push(data[i].id);
                }
            }
            layer.confirm('删除选中的' + ids.length + '条数据？', function (index) {
                    $.ajax({
                        type: 'delete',
                        url: "/article/" + ids,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        data: {
                            "ids": ids
                        },
                        success: function (result) {
                            layer.msg(result.msg, {icon: 1, time: 2000});

                            //数据删除后,当前页面执行即时刷新
                            table.reload('tableList', {
                                contentType: "application/x-www-form-urlencoded",
                                page: {
                                    curr: $(".layui-laypage-em").next().html()
                                },
                                url: '/article/articleList',
                                method: 'get'
                            });
                        }, error: function (e) {
                            console.log(e, 'error');
                            layer.msg("删除失败！", {icon: 2, time: 2000});
                        }
                    });
                    layer.close(index);
                }
            )
        }
    };
    $('.batchDel').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    table.on('tool(article_bar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var ids = data.id;

        switch (layEvent) {
            case 'edit':
                //根据编辑行为为form隐藏项赋值
                data.action = 'updateArticle';
                data.request_type = 'post';
                open_form("#open_div", data, '更新文章', '680px', '282px');
                break;
            case 'del':
                layer.confirm('是否删除？', function (index) {
                    obj.del(); //删除对应行(tr)的DOM结构，并更新缓存
                    //后端执行删除
                    $.ajax({
                        type: "delete",
                        url: "/article/" + ids,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        success: function (result) {
                            layer.msg(result.msg, {icon: 1, time: 2500});
                        }, error: function (e) {
                            console.log(e, 'error');
                            layer.msg("删除失败！", {icon: 2, time: 2500});
                        }
                    });
                    layer.close(index);
                });
                break;
        }
    });

    form.on('submit(update_submit)', function (data) {
        var uri = data.field.action;
        var type = data.field.request_type;
        console.log(data);
        $.ajax({
            type: type,
            url: '/article/' + uri,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: "json",
            success: function (result) {
                if (result.code == "0") {
                    table.reload('tableList', {
                        contentType: "application/x-www-form-urlencoded",
                        page: {
                            curr: 1
                        },
                        url: '/article/articleList',
                        method: 'get'
                    });
                    layer.msg(result.msg, {icon: 1, time: 2500});
                } else {
                    layer.alert(result.msg, {icon: 2}, function () {
                        layer.close(index);
                    });
                }
            }
        });
        layer.close(index); //关闭弹出层
        return false;
    });

    //监听搜索按钮提交事件
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count !== 0) {
            //根据返回的json字符串模糊查询
            tableReload('tableList', formData, "application/json;charset=utf-8", '/article/findByName', 'post');
        } else {
            parent.layer.msg('请先输入查询条件！', {icon: 2, time: 2500});
        }
        return false;
    });
});