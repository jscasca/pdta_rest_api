package com.pd.api.mail;

import com.pd.api.entity.VerificationToken;

public interface Mailer {

    public void sendVerificationMail(VerificationToken token);
}
