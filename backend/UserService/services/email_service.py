import logging
import os
import smtplib
from email.mime.text import MIMEText

from backend.UserService.user_config import TEST_MESSAGE
from backend.env_variables import EMAIL_LOGIN, EMAIL_PASSWORD, EMAIL_TEST, DB_HOST


class EmailService:
    def __init__(self):
        logging.basicConfig(level=logging.INFO)
        self.logger = logging.getLogger(__name__)

        self.smtpObj = smtplib.SMTP('smtp.gmail.com', 587)

    def test_send(self) -> None:
        try:
            self.smtpObj.starttls()
            self.smtpObj.login(EMAIL_LOGIN, EMAIL_PASSWORD)

            self.smtpObj.sendmail(EMAIL_LOGIN, EMAIL_TEST, TEST_MESSAGE.as_string())

            self.smtpObj.quit()

            self.logger.info(f"(Send test mail) Successful sent test message to {EMAIL_TEST}")
        except Exception as e:
            self.logger.error(f"(Send test mail) Error: {e}")
            raise

    def send_link(self, code: str, to_email: str):
        try:
            self.smtpObj.starttls()
            self.smtpObj.login(EMAIL_LOGIN, EMAIL_PASSWORD)
            S = f"http://93.183.70.168:8000/api/v1/user/verify/"
            #S = f"http://localhost:8000/api/v1/user/verify/"
            msg = MIMEText(f'<a href={S + code}>Click to verify</a>', 'html')
            msg['From'] = EMAIL_LOGIN
            msg['To'] = to_email
            msg['Subject'] = 'PmC pYtHoN VeRiFy'

            self.smtpObj.sendmail(EMAIL_LOGIN, to_email, msg.as_string())

            self.smtpObj.quit()

            self.logger.info(f"(Send link) Successful sent link to {to_email}")
        except Exception as e:
            self.logger.error(f"(Send link) Error: {e}")
            raise
