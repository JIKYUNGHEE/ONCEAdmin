<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>고객 관리</title>
<style type="text/css">
@import url('//fonts.googleapis.com/earlyaccess/nanumgothic.css');
	table{
		border-collapse: collapse;
		width : 100%; 
		border : 1 solid; 
		border-color: #bcbcbc;
	}
	
	.tbTile{
		color: black;
		font-size: 18px;
		padding: 7px;
		/* font-family: 함초롬바탕 확장, 나눔손글씨 붓, 휴면매직체; */
		font-family: 'Nanum Gothic', sans-serif;
		font-weight: bold;
	}
	
	tr{
		text-align: center;
	}
	
	tr:hover{
		background-color: #f5f5f5;
	}
	
	.button{
		 background-color : #E7E7E7;
		 border : none;
		 padding: 8px 15px;
		 text-align: center;
		 text-decoration: none;
		 font-size: 15px;
		 font-family: 'Nanum Gothic', sans-serif;
		 font-weight: bold;
	}
	.select{
		font-size: 15px;
		font-weight: bold;
	}
</style>
</head>
<body>
	<div>
		<h3>현재 검색된 회원 총 명</h3>
		<div style="text-align: right;">
			<span class="select">	
				회원 등급 
				<select>
					<option value="1">Option 1</option>
					<option value="2">Option 2</option>
					<option value="3">Option 3</option>
				</select>  
				 &nbsp; &nbsp;가입일 
				<select>
					<option value="2017">2017</option>
					<option value="2016">2016</option>
					<option value="2015">2015</option>
				</select>
				<select>
					<option value="10">10</option>
					<option value="9">9</option>
					<option value="8">8</option>
				</select>	
			</span>
		</div>
		<br/>
		<table>
			<tr style="text-align: center; background-color: #E7E7E7">
				<td width="5%"><input type="checkbox"></td>
				<td width="15%" class="tbTile">고객 번호</td>
				<td width="15%" class="tbTile">아이디</td>
				<td width="15%" class="tbTile">이름</td>
				<td width="20%" class="tbTile">연락처</td>
				<td width="10%" class="tbTile">회원 등급</td>
				<td width="20%" class="tbTile">가입일</td>
			</tr>
			<c:forEach items="${ customerList }" var="customer">
			<tr>
				<td><input type="checkbox"></td>
				<td>${ customer.memNo }</td>
				<td>${ customer.id }</td>
				<td>${ customer.name }</td>
				<td>${ customer.telephone }</td>
				<td>등급</td>
				<td>${ customer.date }</td>
			</tr>
		</c:forEach>
		</table>
		<br/>
		<div style="text-align: right;">
			<span>	
				<input type="button" class="button" value="삭제">
			</span>
		</div>
	</div>
</body>
</html>