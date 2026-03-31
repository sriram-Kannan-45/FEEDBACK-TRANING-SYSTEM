<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Training Feedback System - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>Training Feedback System</h1>
            <p>Sign in to access your account</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="role">Login As</label>
                <select name="role" id="role" class="form-control" required>
                    <option value="">-- Select Role --</option>
                    <option value="admin">Administrator</option>
                    <option value="trainer">Trainer</option>
                    <option value="participant">Participant</option>
                </select>
            </div>

            <div class="form-group">
                <label for="userId">User ID</label>
                <input type="text" id="userId" name="userId" class="form-control" placeholder="Enter your ID" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Sign In</button>
        </form>

        <div style="margin-top: 20px; text-align: center;">
            <p>New Participant? <a href="${pageContext.request.contextPath}/register">Register Here</a></p>
        </div>

        <div style="margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee; font-size: 12px; color: #666;">
            <strong>Demo Credentials:</strong><br>
            Admin: ID=1, Password=admin123<br>
            Trainer: Register first, then get approved by Admin
        </div>
    </div>
</body>
</html>
