<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file ="../include/header.jsp" %>
	
	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
					<!-- box header -->
					<div class="box-header">
						<h3 class="box-title">
							READ PAGE
						</h3>
					</div>
					<form action="updatePage" method="post" role="form">
						<!-- box body -->
						<div class="box-body">
							<input type="hidden" name="bno" value="${board.bno}">
							<div class="form-group">
								<label>Title</label>
								<input type="text" name="title" class="form-control" value="${board.title}">
							</div>
							<div class="form-group">
								<label>Content</label>
								<textarea rows="10" cols="" name="content" class="form-control">${board.content}</textarea>
							</div>
							<div class="form-group">
								<label>Writer</label>
								<input type="text" class="form-control" value="${board.writer}" readonly="readonly">
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

<%@ include file ="../include/footer.jsp" %>