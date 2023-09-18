const check_port_host_xml_filepath = (wssHost, wssPort, xmlFilepath) => {
    /*
        checkWssHost
        checkWssPort
        checkXmlFilepath
        POST create web-socket session
        SUBSCRIBE to web-socket heartbeat event
    */

    // return true if it is ok OR false
    const checkWssHost = (wssHost) => {
        const ipv4_host_regexp = /^((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)\.?\b){4}$/;

        return wssHost.match(ipv4_host_regexp) != null;
    }

    const checkWssPort = (wssPort) => {
        const ipv4_port_regexp = /\d{2,5}/;

        return wssPort.match(ipv4_port_regexp) != null;
    }

    const checkXmlFilepath = (xmlFilepath) => {
        const xml_filepath_regexp = /^.*\.xml$/;

        return xmlFilepath.match(xml_filepath_regexp) != null;
    }

    const wssHostIsOk = () => {
        return true;
    }

    // checkWssHost
    const wssHostIsWrong = () => {
        $('#verify-host-and-port-and-xml').text("Wss host is unacceptable. Please enter the value manually following the pattern '0.0.0.0'.");
        $('.ui.modal').modal({blurring: true}).modal('show');

        return false;
    }

    const wssPortIsOk = () => {
        return true;
    }

    const wssPortIsWrong = () => {
        $('#verify-host-and-port-and-xml').text("Wss port is unacceptable. Please enter the value manually following the pattern '1234'.");
        $('.ui.modal').modal({blurring: true}).modal('show');

        return false;
    }

    const xmlFilepathIsOk = () => {
        return true;
    }

    const xmlFilepathIsWrong = () => {
        $('#verify-host-and-port-and-xml').text("Xml filepath is unacceptable. Please enter the value manually following the pattern '*.xml'.");
        $('.ui.modal').modal({blurring: true}).modal('show');

        return false;
    }

    const wssHostResult = checkWssHost(wssHost) ? wssHostIsOk() : wssHostIsWrong();

    const wssPortResult = checkWssPort(wssPort) ? wssPortIsOk() : wssPortIsWrong();

    const xmlFilepathResult = checkXmlFilepath(xmlFilepath) ? xmlFilepathIsOk() : xmlFilepathIsWrong();

    return wssHostResult && wssPortResult && xmlFilepathResult;
}

var items = [
                     {
                         id: 1,
                         priority: 1,
                         value: 'reacttreetable@simple.com'
                     },
                     {
                         id: 2,
                         priority: 2,
                         parentId: 1,
                         value: 'reacttreetable@simple.com'
                     },
                     {
                         id: 3,
                         priority: 3,
                         parentId: 1,
                         value: 'reacttreetable@simple.com'
                     },
                     {
                         id: 4,
                         priority: 4,
                         parentId: 1,
                         value: 'reacttreetable@simple.com'
                     }];
$(() => {
    $("#connect-disconnect-button" ).on("click", () => {
        const connectionIsEstablished = () => {
            let isConnected = $("#connect-disconnect-button" ).attr('isConnected');

            return isConnected === 'true';
        }

        const connectToServer = () => {
            const changeConnectStateToDisconnect = () => {
               $("#connect-disconnect-button" ).html('Disconnect');
               $("#connect-disconnect-button" ).removeClass('green');
               $("#connect-disconnect-button" ).addClass('red');

               $("#connect-disconnect-button" ).attr('isConnected', 'true');

                return true;
            }

            const actualConnectToServer = () => {
                //connect to server

                // change state of button
                changeConnectStateToDisconnect();

                return true;
            }

            const connectionBooleanResult = () => {
                return false;
            }

            let wssPort = $('#wss-port').val();
            let wssHost = $('#wss-host').val();
            let xmlFilepath = $('#xml-filepath').val();

            return check_port_host_xml_filepath(wssHost, wssPort, xmlFilepath) ? actualConnectToServer() : connectionBooleanResult();
        }

        const disconnectFromServer = () => {
            const changeDisconnectStateToConnect = () => {
              $("#connect-disconnect-button" ).html('Connect');
              $("#connect-disconnect-button" ).removeClass('red');
              $("#connect-disconnect-button" ).addClass('green');

              $("#connect-disconnect-button" ).attr('isConnected', 'false');

              return true;
            }

            changeDisconnectStateToConnect();

            return true;
        }

        return connectionIsEstablished() ? disconnectFromServer() : connectToServer();
    });

    function flatListToTree(items) {
            const getChild = (item, level, allLevel) => {
                return items.filter(v => v.parentId === item.id)
                    .map(v => {
                            const temp = {
                                ...v,
                                level,
                                children: getChild(v, level + 1, level === 0 ? v.id : `${allLevel}_${v.id}`),
                                partLevel: level === 0 ? v.id : `${v.parentId}_${v.id}`,
                                ...(level === 0 ? {
                                    allLevel: v.id
                                } : {
                                    allLevel: [allLevel, v.id].join('_')
                                }),
                            };
                            return [temp].concat(...temp.children);
                        }
                    );
            };

            return [].concat(...getChild({ id: undefined }, 0, undefined))
        };

    $(document.body).delegate('.expand', 'click', function () {
            var level = $(this).attr('data-level');
            var partLevel = $(this).attr('data-part-level');
            var allLevel = $(this).attr('data-all-level');
            var isOpen = $(this).attr('data-is-open');
            var trsDiv = $('.tree-table').find('tbody tr');
            var trsArray = $(trsDiv);
            if (isOpen === '1') {
                for(var i = 0;i < trsArray.length - 1; i++) {
                    var tempTr = $(trsArray[i]);
                    var trLevel = tempTr.attr('data-level');
                    var trPartLevel = tempTr.attr('data-part-level');
                    var trAllLevel = tempTr.attr('data-all-level');
                    var contain = trAllLevel.split('_')[Number(level)]; // 通过循环出来的tr的all_level获取选中等级的id
                    var curr = partLevel.split('_'); // 通过获取选中的part_level的最后一个元素获取选中等级的id
                    // 判断是否相等，
                    if (contain && contain === curr[curr.length - 1] && partLevel !== trPartLevel) {
                        tempTr.removeClass('show');
                        tempTr.addClass('hidden');
                    }
                }
                $(this).text('+');
                $(this).attr('data-is-open', '0');
            } else {
                for(var i = 0;i < trsArray.length - 1; i++) {
                    var tempTr = $(trsArray[i]);
                    var trLevel = tempTr.attr('data-level');
                    var trPartLevel = tempTr.attr('data-part-level');
                    var trAllLevel = tempTr.attr('data-all-level');
                    var contain = trAllLevel.split('_')[Number(level)]; // 通过循环出来的tr的all_level获取选中等级的id
                    var curr = partLevel.split('_'); // 通过获取选中的part_level的最后一个元素获取选中等级的id
                    // 判断是否相等，
                    if (contain && contain === curr[curr.length - 1] && Number(trLevel) > (Number(level))) {
                        var span = $(tempTr.children()[0].children[Number(trLevel)]);
                        var isOpen = $(span).attr('data-is-open');
                        var childrenCount = $(span).attr('data-count');
                        tempTr.removeClass('hidden');
                        tempTr.addClass('show');
                        // pLevel != -1 并且有子级的情况下，判断pLevel的开关状态，关闭则不展开其下级元素
                        if (isOpen && isOpen === '0' && Number(childrenCount) > 0) { // 下级折叠状态
                            i = i + Number(childrenCount);
                        } else {
                          if (isOpen === '1') {
                            $(span).attr('data-is-open', '1');
                            $(span).text('-');
                            tempTr.removeClass('hidden');
                            tempTr.addClass('show');
                          }
                        }
                    }
                }
                $(this).text('-');
                $(this).attr('data-is-open', '1');
            }
        });

    function countChildren(node) {
            var sum = 0,
              children = node && node.length ? node : node.children,
              i = children && children.length;

            if (!i) {
                sum = 0;
            } else {
                while (--i >= 0) {
                    if (node && node.length) {
                        sum++;
                        countChildren(children[i]);
                    } else {
                        sum += countChildren(children[i]);
                    }
                }
            }
            return sum;
        }

    function createRows() {
            var fragments = document.createDocumentFragment();
            var opts = flatListToTree(items);
            for (var i = 0; i < opts.length; i++) {
                var item = opts[i];
                var trEle = document.createElement('tr');
                $(trEle).attr('data-part-level', item.partLevel);
                $(trEle).attr('data-all-level', item.allLevel);
                $(trEle).attr('data-level', item.level);
                $(trEle).attr('data-count', countChildren(item));
                var tdEle1 = document.createElement('td');
                for (var j =0; j <= item.level; j++) {
                    var spanEle = document.createElement('span');
                    $(spanEle).addClass('tree-table-space-block');
                    $(spanEle).attr('data-part-level', item.partLevel);
                    $(spanEle).attr('data-all-level', item.allLevel);
                    $(spanEle).attr('data-level', item.level);
                    var iEle = document.createElement('i');
                    if (j === item.level) {
                        if (item.children && item.children.length > 0) {
                            $(spanEle).addClass('btn-toggle expand');
                            $(spanEle).attr('data-is-open', '1');
                            $(spanEle).attr('data-count', countChildren(item));
                            $(spanEle).text('-');
                        } else {
                            $(spanEle).addClass('last-block');
                            $(spanEle).append(iEle);
                        }
                    } else {
                        $(spanEle).append(iEle);
                    }
                    $(tdEle1).append(spanEle);
                }

                var tdEle0 = document.createElement('td');
                $(tdEle0).css('width', '50px');
                var spanTd0 = document.createElement('span');
                $(spanTd0).addClass('tree-table-td-content');
                $(spanTd0).append("<div class='ui checkbox'><input type='checkbox' name='example'><label></label></div>");
                $(tdEle0).append(spanTd0);

                var spanEle2 = document.createElement('span');
                $(spanEle2).addClass('tree-table-td-content');
                $(spanEle2).text(item.id);
                $(tdEle1).append(spanEle2);

                var tdEle2 = document.createElement('td');
                $(tdEle2).css('width', '200px');
                var spanTd2 = document.createElement('span');
                $(spanTd2).addClass('tree-table-td-content');
                $(spanTd2).text(item.value);
                $(tdEle2).append(spanTd2);

                $(trEle).append(tdEle0);
                $(trEle).append(tdEle1);
                $(trEle).append(tdEle2);

                $(fragments).append(trEle);
            }

            $('#download-table-tree').append(fragments);
        }

    createRows();
});
