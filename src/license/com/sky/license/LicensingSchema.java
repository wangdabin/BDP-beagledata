package com.sky.license;

import net.java.truelicense.core.LicenseConsumerManager;
import net.java.truelicense.core.V2XmlLicenseManagementContext;
import net.java.truelicense.obfuscate.Obfuscate;
import net.java.truelicense.obfuscate.ObfuscatedString;

/**
 * A derived context for license consumer applications. Use this context to configure a LicenseConsumerManager with the required parameters.
 * Here's an example for configuring a licensing schema for consuming MyApp 1.X license keys with a Free Trial Period (FTP) of thirty days:
 * 免费试用期
 * @author qiaolong
 *
 */
public final class LicensingSchema {

    /** Returns the license consumer manager. */
    public static LicenseConsumerManager manager() { return Lazy.cm; }

    private static class Lazy {

        @Obfuscate
        static final String SUBJECT = "MyApp 1.X";

        @Obfuscate
        static final String PUBLIC_KEY_STORE_NAME = "public.ks";

        static final ObfuscatedString PUBLIC_KEY_STORE_PASSWORD =
                new ObfuscatedString(new long[] {
                    0x66c1016da18a6ffdl, 0xbda53ccf9e30c9efl }); /* => "test1234" */

        @Obfuscate
        static final String PUBLIC_CERT_ENTRY_ALIAS = "mykey";

        static final ObfuscatedString PBE_PASSWORD =
                new ObfuscatedString(new long[] {
                    0xc9c00fc60c9db6b8l, 0x4a19910b12cd00c4l }); /* => "test1234" */

        static final int FTP_DAYS = 30;

        @Obfuscate
        static final String FTP_KEY_STORE_NAME = "ftp.ks";

        static final ObfuscatedString FTP_KEY_STORE_PASSWORD =
                new ObfuscatedString(new long[] {
                    0x7b4abf4aed98b47al, 0xb1e13b4bc0854bccl }); /* => "test1234" */

        @Obfuscate
        static final String FTP_KEY_ENTRY_ALIAS = "mykey";

        static final ObfuscatedString FTP_KEY_ENTRY_PASSWORD =
                new ObfuscatedString(new long[] {
                    0x149d045402a96977l, 0xa448f2162811f378l }); /* => "test1234" */

        static final LicenseConsumerManager cm =
                new V2XmlLicenseManagementContext(SUBJECT)
                    .consumer()
                    .manager()
                        .parent()
                            .keyStore()
                                .loadFromResource(PUBLIC_KEY_STORE_NAME)
                                .storePassword(PUBLIC_KEY_STORE_PASSWORD)
                                .alias(PUBLIC_CERT_ENTRY_ALIAS)
                                .inject()
                            .pbe()
                                .password(PBE_PASSWORD)
                                .inject()
                            .storeInUserNode(LicensingSchema.class)
                            .inject()
                        .ftpDays(FTP_DAYS)
                        .keyStore()
                            .loadFromResource(FTP_KEY_STORE_NAME)
                            .storePassword(FTP_KEY_STORE_PASSWORD)
                            .alias(FTP_KEY_ENTRY_ALIAS)
                            .keyPassword(FTP_KEY_ENTRY_PASSWORD)
                            .inject()
//                        .storeInUserNode(TopSecret.class)
                        .build();
    }
 }