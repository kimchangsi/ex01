<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp"%>
<style>
div.uploadDiv {
	overflow: hidden;
}

div.item {
	float: left;
	height: 100px;
	position: relative;
	margin: 0px 3px;
}

div.item .btnDel {
	position: absolute;
	right: 3px;
	top: 3px;
	font-weight: bold;
}
</style>
<script>
	$(function() {
		$(document).on("click", ".btnDel", function() {
			//지울 파일명
			var file = $(this).attr("data-src");
			$(this).parent().hide();
			var $div = $("<input type='hidden' name='fullname'>").val(file);
			$(this).parent().append($div);
			
		})
	})
</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<!-- box header -->
				<div class="box-header">
					<h3 class="box-title">READ PAGE</h3>
				</div>
				<form action="updatePage" method="post" role="form" enctype="multipart/form-data">
					<!-- box body -->
					<div class="box-body">
						<input type="hidden" name="bno" value="${board.bno}"> <input
							type="hidden" name="page" value="${cri.page }"> <input
							type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
						<div class="form-group">
							<label>Title</label> <input type="text" name="title"
								class="form-control" value="${board.title}">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="10" cols="" name="content" class="form-control">${board.content}</textarea>
						</div>
						
						<div class="form-group">
								<label>Files</label>
								<input type="file" name="imgFiles" class="form-control" multiple="multiple">
							</div>
						
						<div class='form-group uploadDiv'>
							<c:forEach var="file" items="${board.files }">
								<div class="item">
									<img src="displayFile?filename=${file }">
									<button type="button" class="btnDel" data-src=${file }>X</button>
								</div>
							</c:forEach>
						</div>
					</div>
					<!-- box footer -->
					<div class="box-footer">
						<button type="submit" class="btn btn-warning">Modify</button>
						<%-- <a href="${pageContext.request.contextPath}/board/delete?bno=${board.bno}"><button type="submit" class="btn btn-danger">Remove</button></a>
							<a href="${pageContext.request.contextPath}/board/listAll"><button type="submit" class="btn btn-primary">Go List</button></a> --%>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>