<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Participant Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container" style="width: 500px;">
        <div class="login-header">
            <h1>Participant Registration</h1>
            <p>Create your account to register for training sessions</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label for="id">Participant ID *</label>
                <input type="number" id="id" name="id" class="form-control" placeholder="Enter unique ID" required>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="name">Full Name *</label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Enter your name" required>
                </div>

                <div class="form-group">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Enter password" required>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email Address *</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="Enter your email" required>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="dept">Department</label>
                    <input type="text" id="dept" name="dept" class="form-control" placeholder="Enter department">
                </div>

                <div class="form-group">
                    <label for="college">College/Organization</label>
                    <input type="text" id="college" name="college" class="form-control" placeholder="Enter college name">
                </div>
            </div>

            <div class="form-group">
                <label for="course">Course/Program</label>
                <input type="text" id="course" name="course" class="form-control" placeholder="Enter course name">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-success btn-block">Register</button>
            </div>
        </form>

        <div style="margin-top: 20px; text-align: center;">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/">Login Here</a></p>
        </div>
    </div>
</body>
</html>
