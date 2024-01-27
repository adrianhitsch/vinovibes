package com.vinovibes.vinoapi.constants;

public class EmailTemplates {

    public static final String OTP_EMAIL_TEMPLATE =
        """
        					<!DOCTYPE html>
        <html>
        	<head>
        		<style>
        			:root {
        				font-size: 16px !important;
        			}
        			* {
        				box-sizing: border-box;
        			}

        			body {
        				background-color: #0f1924;
        				color: #e4e4e4;
        				font-family: "Roboto", Arial, Helvetica, sans-serif;
        				line-height: 1.6;
        				filter: invert(0);
        			}

        			.email {
        				max-width: max-content;
        				margin: 1.25rem auto;
        				padding: 1.25rem;
        				text-align: left;
        			}

        			.name {
        				font-weight: bold;
        			}

        			.code {
        				background: #27303a;
        				border-radius: 0.3rem;
        				padding: 0.8rem 1.2rem;
        				margin: 2rem auto;
        				width: fit-content;
        				font-weight: bold;
        				font-size: 1.3rem;
        				letter-spacing: 0.2rem;
        			}

        			.footer {
        				display: flex;
        				align-items: center;
        				margin-top: 2rem;
        				padding-bottom: 0.625rem;
        				border-bottom: 0.125rem solid #27303a;
        			}

        			.footer .text {
        				margin-left: 1.25rem;
        			}

        			.footer h1 {
        				font-size: 1.3rem;
        				margin-bottom: 0;
        			}

        			.footer p {
        				font-size: 0.8rem;
        				margin-top: 0;
        				font-style: italic;
        				color: #adadad;
        			}

        			.footer .logo {
        				width: 3.75rem;
        				filter: invert(0);
        			}

        			.disclaimer {
        				font-size: 0.7rem;
        				margin-top: 1.25rem;
        				font-style: italic;
        				color: #7f7f7f;
        				max-width: 500px;
        			}

        			.disclaimer .email-link {
        				color: #7f7f7f !important;
        				text-decoration: underline;
        			}

        			@media (prefers-color-scheme: dark) {
        				body {
        					background-color: #0f1924;
        					color: #e4e4e4;
        				}

        				.message .code {
        					background: #27303a;
        				}
        			}

        			@media (prefers-color-scheme: light) {
        				body {
        					background-color: #0f1924;
        					color: #e4e4e4;
        				}

        				.message .code {
        					background: #27303a;
        				}
        			}
        		</style>
        	</head>

        	<body>
        		<div class="email">
        			<h2>Verifiziere deinen VinoVibes Account</h2>
        			<p class="name">Hey {{NAME}}!</p>
        			<p style="margin-bottom: 0">Bitte verifiziere deinen Account.</p>
        			<p style="margin-top: 0">Dein persönlicher Code ist eine Stunde gültig.</p>
        			<p class="code">{{OTP}}</p>

        			<div class="footer">
        				<img
        					src="https://vino-vibes.com/publicLinks/logo.png"
        					alt="VinoVibes Logo"
        					class="logo"
        				/>
        				<div class="text">
        					<h1>VinoVibes</h1>
        					<p>- Geschmack verbindet</p>
        				</div>
        			</div>
        			<p class="disclaimer">Dies ist eine automatisch generierte E-Mail. Bitte antworte nicht darauf. Falls du dich an den Support wenden möchtest, schreibe eine E-Mail an <a class="email-link" href="mailto:support@vino-vibes.com">support@vino-vibes.com</a>.</p>
        		</div>
        	</body>
        </html>
                """;
}
