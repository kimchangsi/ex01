<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%@ include file="../include/header.jsp" %>
	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">
							List All Page
						</h3>
					</div>
						<div class="box-body">
							<table class="table table-bordered">
								<tr>
									<th style="width:10px;">BNO</th>
									<th>Title</th>
									<th>Writer</th>
									<th>RegDate</th>
									<th style="width: 40px;">ViewCnt</th>
								</tr>
								<c:forEach var="board" items="${list }"> 
									<tr>
										<td>${board.bno }</td>
										<td><a href="read?bno=${board.bno }">${board.title }</a></td>
										<td>${board.writer }</td>
										<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd  kk:mm"/> </td>   
										<td><span class="badge bg-red">${board.viewcnt }</span></td> 
									</tr> 
								</c:forEach> 
							</table>							
						</div>
						
						<div class="box-footer">
							<a href="${pageContext.request.contextPath}/board/register"><button class="btn btn-primary">create</button></a>
						</div>
				</div>
			</div>
		</div>
	</section>
<%@ include file="../include/footer.jsp" %>