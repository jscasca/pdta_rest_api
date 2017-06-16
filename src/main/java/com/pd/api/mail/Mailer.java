package com.pd.api.mail;

import com.pd.api.entity.Language;
import com.pd.api.entity.VerificationToken;

public interface Mailer {

    void sendVerificationMail(VerificationToken token);

    void sendVerificationMail(VerificationToken token, Language lang);
}
