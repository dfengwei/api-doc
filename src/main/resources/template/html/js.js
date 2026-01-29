$(function () {
    // 获取浏览器中缓存的终端选中状态
    let terminalCheckedStatus = JSON.parse(localStorage.getItem('terminal-checked-status')) || {};

    // 生成新的终端选中状态
    let newTerminalCheckedStatus = {};
    $('#terminal-nav input:checkbox').each(function (index, element) {
        let me = $(this);
        let terminalName = me.attr('data-terminal-name');
        newTerminalCheckedStatus[terminalName] = terminalCheckedStatus[terminalName] || 0;
    });
    localStorage.setItem('terminal-checked-status', JSON.stringify(newTerminalCheckedStatus));

    // 终端Checkbox选中状态变更
    $('#terminal-nav input:checkbox').change(function () {
        let me = $(this);
        let terminalId = me.attr('data-terminal-id');
        let terminalName = me.attr('data-terminal-name');
        if (me.is(':checked')) {
            // 显示终端相关菜单
            $('#main-nav ul[data-terminal-id="' + terminalId + '"]').removeClass('hidden');
            // 显示终端相关内容
            $('#main div[data-terminal-id="' + terminalId + '"]').removeClass('hidden');
            // 记录入localStorage
            newTerminalCheckedStatus[terminalName] = 1;
            localStorage.setItem('terminal-checked-status', JSON.stringify(newTerminalCheckedStatus));
        } else {
            // 隐藏终端相关菜单
            $('#main-nav ul[data-terminal-id="' + terminalId + '"]').addClass('hidden');
            // 隐藏终端相关内容
            $('#main div[data-terminal-id="' + terminalId + '"]').addClass('hidden');
            // 记录入localStorage
            newTerminalCheckedStatus[terminalName] = 0;
            localStorage.setItem('terminal-checked-status', JSON.stringify(newTerminalCheckedStatus));
        }
    });

    // 菜单分组折叠按钮
    $('#main-nav span.arrow').click(function () {
        let arrow = $(this);
        if (arrow.hasClass("expended")) {
            arrow.removeClass("expended");
            $(arrow.parents(".group")[0]).children(".apis, .content").hide(100);
        } else {
            arrow.addClass("expended");
            $(arrow.parents(".group")[0]).children(".apis, .content").show(100);
        }
    });
    $('#main-nav div.title').on("dblclick", function () {
        let arrow = $(this).children(".arrow");
        if (arrow.hasClass("expended")) {
            arrow.removeClass("expended");
            $(arrow.parents(".group")[0]).children(".apis, .content").hide(100);
        } else {
            arrow.addClass("expended");
            $(arrow.parents(".group")[0]).children(".apis, .content").show(100);
        }
    });

    // 字段列表折叠按钮
    $('#main .api .toggle').click(function () {
        let toggle = $(this);

        let expended = !toggle.hasClass("expended");

        toggle.toggleClass("expended");

        // 数据所在行
        let currentTr = $(toggle.parents("tr")[0]);
        // 数据键值
        let currentDataKey = currentTr.attr('data-key');
        // 遍历数据所在行后的所有行
        currentTr.nextAll("tr").each(function () {
            let tr = $(this);
            let dataKey = tr.attr('data-key');
            if (dataKey && dataKey.startsWith(currentDataKey)) {
                if (expended) {
                    tr.find(".toggle").addClass("expended");
                    tr.show(100);
                } else {
                    tr.hide(100);
                }
            }
        });
    });

    // 根据localStorage初始化终端选中状态
    $('#terminal-nav input:checkbox').each(function (index, element) {
        let me = $(this);
        let terminalName = me.attr('data-terminal-name');
        if (newTerminalCheckedStatus[terminalName] == 1) {
            me.prop('checked', true).trigger('change');
        }
    });

    // 接口内容section悬浮后，菜单变色
    $('#main .api').hover(function () {
        let me = $(this);
        let apiId = me.attr("id");
        let nav = $('a.api[href="#' + apiId + '"]');
        nav.addClass("hover");
    });

    // 接口菜单点击后变更选中状态
    $('#main-nav .api').click(function () {
        let me = $(this);
        $('#main-nav .api').removeClass("selected");
        me.addClass("selected");
    });

    // 通过带锚点的url访问，菜单自动选中
    if (location.hash) {
        $('#main-nav .api').removeClass("selected");
        $(`#main-nav .api[href="${location.hash}"]`).addClass('selected');
    }
});