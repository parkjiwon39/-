/**
 * 댓글 작성 스크립트
 */
let replyService=(function(){
	//직접 호출할 수 없는 함수
	function add(reply,callback){
		console.log("add method 실행");
		console.log(reply);
		$.ajax({
			url:'/replies/new',
			type : 'post',
			contentType:'application/json',
			data : JSON.stringify(reply),
			success : function(result){
				//console.log(result);
				if(callback){
					callback(result);
				}
			},
			error : function(xhr,txtstatus,error){
				console.log(xhr.responseText);
			}
		})
	}//add 종료
	
	function getList(param,callback){
		let bno = param.bno;
		let page = param.page || 1;
		
		console.log("bno : "+bno);
		console.log("page : "+page);
		
		let url = "/replies/pages/"+bno+"/"+page;
		
		$.getJSON(url, function(data) {
			console.log(data)
			if(callback){
				//data.replyCnt : 전체 댓글 수
				//data.list : 댓글 리스트
				callback(data.replyCnt,data.list);
			}
		})
		
	}//getList종료
	
	function remove(rno,replyer,callback,error){
		console.log("rno : "+rno+"replyer"+replyer);
		$.ajax({
			url : '/replies/'+rno, //http://localhost:8080/replies/3
			type : 'delete',
			contentType : "application/json",
			data : JSON.stringify({
				replyer : replyer
			}),
			success : function(result){
				if(callback){
					callback(result);
				}
			},
			error:function(xhr,status,err){
				if(error){
					error(xhr.responseText);
				}
			}
		})
	}//remove 종료
	
	function update(reply,callback,error){
		$.ajax({
			url : '/replies/'+reply.rno,//http://localhost:8080/replies/3
			type : 'put',
			contentType : 'application/json',
			data : JSON.stringify(reply),
			success : function(result){
				if(callback){
					callback(result);
				}
			},
			error:function(xhr,status,err){
				if(error){
					error(xhr.responseText);
				}
			}
		})
	}//get종료
	function displayTime(timeVal){
		let today = new Date();
		
		let gap = today.getTime() - timeVal;
		let dateObj = new Date(timeVal);		
		
		if(gap < (1000*60*60*24)){
			let hh = dateObj.getHours();
			let mm = dateObj.getMinutes();
			let ss = dateObj.getSeconds();
			return [(hh > 9 ? '':'0')+hh,':',(mm > 9 ? '':'0')+mm, 
				':', (ss > 9 ? '':'0')+ss].join("");
		}else{
			let yy = dateObj.getFullYear();
			let mm2 = dateObj.getMonth()+1;
			let dd = dateObj.getDate();
			return [yy,"/",(mm2 > 9 ? '':'0')+mm2,"/",(dd > 9 ? '':'0')+dd].join("");
		}
	}
	function get(rno,callback,error){
		$.getJSON({
			url : '/replies/'+rno,
			type : 'get',
			success : function(result){
				if(callback){
					callback(result);
				}
			},
			error : function(xhr,status,err){
				if(error){
					error(xhr.responseText);
				}
			}
		})
	}//get종료
	
	
	
	//return구문에 의해서 각 함수의 결과를 리턴 받기
	return {
		add:add,
		getList:getList,
		remove:remove,
		update:update,
		get:get,
		displayTime:displayTime
		};
})();