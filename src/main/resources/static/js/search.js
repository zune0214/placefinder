const baseObj = function(){
    const urls = {
        kakao : "https://map.kakao.com/link/map/{id}"
    };

    const detailList = [
        {title : "지번", key : "addressName"},
        {title : "도로명", key : "roadAddressName"},
        {title : "전화번호", key : "phone"}
    ];

    const viewItem = function(item){
        let html = '<div class="alert search-place-info"> <h5 class="display-5" style="cursor: pointer;" data-id="'+item.id+'">'
            + item.placeName + '<small class="text-muted"> | ' + item.categoryName + '</small></h5>';
        for (let detail of detailList) {
            html += viewDetail(detail.title, item[detail.key]);
        }
        html += '</div>';
        return html;
    };

    const viewDetail = function(type, data){
        return !data?'':' <span class="badge badge-secondary">'+type+'</span> <code> '+data+'</code> ';
    };

    const getUrl = function(sourceType, id){
        if(urls[sourceType]){
            return urls[sourceType].replace('{id}', id);
        }
    };

    const getItemListParser = function(item){
        let html = '<p class="lead m-3">"' + item.keyword + '" 검색 결과 <b>' + item.pageSize + '</b>건이 검색되었습니다.</p>';
        for (let v of item.places) {
            html += viewItem(v);
        }
        return html;
    };

    const viewHtml = function(object, html, endfunction){
        object.html(html);
        if(endfunction){
            endfunction();
        }
    };

    const makePageNavigation = function(meta){
        let active = ' active';
        let disable = ' disabled';
        let end = Math.ceil(meta.pageSize/15);
        let element = '<li class="page-item' + (meta.pageNo == 1 ? disable : '') + '"><a class="page-link" href="#">이전</a></li>';
        for(let i=1; i<end+1; i++){
            element += '<li class="page-item' + (meta.pageNo == i ? active : '') + '"><a class="page-link" href="#"> ' + i + ' </a></li>'
        }
        element += '<li class="page-item' + (meta.pageNo == end ? disable : '') + '"><a class="page-link" href="#">다음</a></li>'
        return element;
    };

    const getItemRankParser = function(list){
        let html = '';
        for (let i=0; i<list.length; i++) {
            html += '<li class="list-group-item rank d-flex justify-content-between" data-keyword="'
                + list[i].keyword + '"> <b>' + (i+1) + '. </b> ' + list[i].keyword
                + ' <span class="badge badge-primary badge-pill"> ' + list[i].count + '</span></li>';
        }
        return html;
    };

    let currentPage = 1;
    let currentKeyword = '';
    let currentRankBody;

    return {
        rank : function(rankBody){
            currentRankBody = rankBody ? rankBody : currentRankBody;

            $.ajax({
                method: 'GET'
                , url: '/api/v1/keywordRank'
                , xhrFields: {
                    withCredentials: true
                }
                , error : function(result){
                    alert(result.description);
                }
            }).then(function (result) {
                if(!result.result){
                    alert(result.description);
                    return false;
                }

                viewHtml(currentRankBody, getItemRankParser(result.data.ranking), function(){
                    $('.list-group-item .rank').on('click', function(){
                        $('#searchKeyword').val($(this).data('keyword'));
                        fnSearch(1);
                    });
                });

            });
        },
        search : function(keyword, body, navi, pageNo){
            currentPage = pageNo ? pageNo : 1;
            currentKeyword = keyword;
            if(!currentKeyword){
                alert('검색 키워드를 입력 하신 후 다시 검색해주세요!');
                return;
            }
            $.ajax({
                method: 'POST'
                , url: '/api/v1/search/' + currentKeyword + '/' + currentPage
                , xhrFields: {
                    withCredentials: true
                }
                , error : function(result){
                    alert(result.description);
                }
            }).then(function (result) {
                if(!result.result){
                    alert(result.description);
                    return false;
                }

                viewHtml(body, getItemListParser(result.data), function(){
                    $('.search-place-info .display-5').on('click', function(){
                        window.open(getUrl(result.data.searchSource, $(this).data('id')), '_blank');
                    });
                });

                viewHtml(navi, makePageNavigation(result.data), function(){
                    $('.page-item .page-link').on('click', function(){
                        let pageNo = $(this).html().trim();
                        if(pageNo == '이전'){
                            pageNo = (currentPage*1) - 1;
                        }else if(pageNo == '다음'){
                            pageNo = (currentPage*1) + 1;
                        }
                        fnSearch(pageNo);
                    });
                });

                baseObj.rank(currentRankBody);
            });
        }
    }
}();