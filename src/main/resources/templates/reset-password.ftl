<html>
    <body>
        <h2>Reset Password</h2>
        <p>Dear ${user.getFirstName()} ${user.getLastName()},</p>
        <p>We have received a request to reset your password for your account at our website. To complete the process, please click the link below:</p>
        <p><a href="${resetLink}">${resetLink}</a></p>
        <p>If you did not request this change, please ignore this email and your password will remain unchanged.</p>
        <p>Best regards,</p>
        <p>The Support Team</p>
    </body>
</html>
