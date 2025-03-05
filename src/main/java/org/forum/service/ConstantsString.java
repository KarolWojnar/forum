package org.forum.service;

public final class ConstantsString {
    private ConstantsString() {
    }

    public static final String ACTIVATION_EMAIL_SUBJECT = "Activate your account";
    public static final String ADMIN_INVITATION_SUBJECT = "Admin Invitation";
    public static final String RESET_PASSWORD_SUBJECT = "Reset your password";

    public static final String ACTIVATION_EMAIL_TEMPLATE = """
        <html>
        <body>
            <h2 style="color: #007bff;">Welcome to our forum!</h2>
            <p>Click the button below to activate your account:</p>
            <p><a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
             color: white; text-decoration: none; border-radius: 5px;'>Activate Account</a></p>
            <p>If the button doesn't work, use the following link:</p>
            <p><a href='%s'>%s</a></p>
            <p>Best regards,<br>Forum Team</p>
        </body>
        </html>
    """;

    public static final String ADMIN_INVITATION_TEMPLATE = """
        <html>
        <body>
            <h2 style="color: #007bff;">You have been invited to become an admin!</h2>
            <p>Click the button below to complete your admin registration:</p>
            <p><a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
             color: white; text-decoration: none; border-radius: 5px;'>Register as Admin</a></p>
            <p>If the button doesn't work, use the following link:</p>
            <p><a href='%s'>%s</a></p>
            <p>This invitation will expire on: %s</p>
            <p>Best regards,<br>Forum Team</p>
        </body>
        </html>
    """;

    public static final String RESET_PASSWORD_TEMPLATE = """
        <html>
        <body>
            <h2 style="color: #007bff;">Reset your password</h2>
            <p>Click the button below to reset your password:</p>
            <p><a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
             color: white; text-decoration: none; border-radius: 5px;'>Reset Password</a></p>
            <p>If the button doesn't work, use the following link:</p>
            <p><a href='%s'>%s</a></p>
            <p>Best regards,<br>Forum Team</p>
        </body>
        </html>
    """;
}
