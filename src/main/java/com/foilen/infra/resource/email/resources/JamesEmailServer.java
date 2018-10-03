/*
    Foilen Infra Resource Email
    https://github.com/foilen/foilen-infra-resource-email
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.email.resources;

import com.foilen.infra.resource.application.Application;
import com.foilen.infra.resource.composableapplication.AttachablePart;
import com.foilen.infra.resource.composableapplication.parts.AttachableMariaDB;
import com.foilen.infra.resource.machine.Machine;
import com.foilen.infra.resource.mariadb.MariaDBDatabase;
import com.foilen.infra.resource.mariadb.MariaDBUser;
import com.foilen.infra.resource.unixuser.UnixUser;
import com.foilen.infra.resource.webcertificate.WebsiteCertificate;

/**
 * This is a common Email Server resource. Real implementations will extend this class and use everything linked to it.<br/>
 * Links to:
 * <ul>
 * <li>{@link UnixUser}: (1) RUN_AS - The user that executes the email server</li>
 * <li>{@link Machine}: (optional / many) INSTALLED_ON - The machines where to install that service</li>
 * <li>{@link MariaDBDatabase}: (1) USES - The database to use</li>
 * <li>{@link MariaDBUser}: (1) USES - The database username and password to use</li>
 * <li>{@link WebsiteCertificate}: (1) USES_SMTP - Needed for TLS.</li>
 * <li>{@link WebsiteCertificate}: (1) USES_IMAP - Needed for TLS.</li>
 * <li>{@link WebsiteCertificate}: (1) USES_POP3 - Needed for TLS.</li>
 * <li>{@link AttachablePart}: (optional / many) ATTACHED - The parts to attach (at least one {@link AttachableMariaDB})</li>
 * </ul>
 *
 * Manages:
 * <ul>
 * <li>{@link Application}: The James application</li>
 * </ul>
 */
public class JamesEmailServer extends EmailServer {

    public static final String RESOURCE_TYPE = "Apache James Email Server";

    public static final String PROPERTY_DISABLE_BOUNCE_NOTIFY_POSTMASTER = "disableBounceNotifyPostmaster";
    public static final String PROPERTY_DISABLE_BOUNCE_NOTIFY_SENDER = "disableBounceNotifySender";
    public static final String PROPERTY_DISABLE_RELAY_DENIED_NOTIFY_SENDER = "disableRelayDeniedNotifyPostmaster";

    public static final String PROPERTY_ENABLE_DEBUG_LOGS = "enableDebuglogs";
    public static final String PROPERTY_ENABLE_DEBUG_DUMP_MESSAGES_DETAILS = "enableDebugDumpMessagesDetails";

    private boolean disableBounceNotifyPostmaster;
    private boolean disableBounceNotifySender;
    private boolean disableRelayDeniedNotifyPostmaster;

    private boolean enableDebuglogs;
    private boolean enableDebugDumpMessagesDetails;

    @Override
    public String getResourceDescription() {
        return "Apache James email server";
    }

    public boolean isDisableBounceNotifyPostmaster() {
        return disableBounceNotifyPostmaster;
    }

    public boolean isDisableBounceNotifySender() {
        return disableBounceNotifySender;
    }

    public boolean isDisableRelayDeniedNotifyPostmaster() {
        return disableRelayDeniedNotifyPostmaster;
    }

    public boolean isEnableDebugDumpMessagesDetails() {
        return enableDebugDumpMessagesDetails;
    }

    public boolean isEnableDebuglogs() {
        return enableDebuglogs;
    }

    public void setDisableBounceNotifyPostmaster(boolean disableBounceNotifyPostmaster) {
        this.disableBounceNotifyPostmaster = disableBounceNotifyPostmaster;
    }

    public void setDisableBounceNotifySender(boolean disableBounceNotifySender) {
        this.disableBounceNotifySender = disableBounceNotifySender;
    }

    public void setDisableRelayDeniedNotifyPostmaster(boolean disableRelayDeniedNotifyPostmaster) {
        this.disableRelayDeniedNotifyPostmaster = disableRelayDeniedNotifyPostmaster;
    }

    public void setEnableDebugDumpMessagesDetails(boolean enableDebugDumpMessagesDetails) {
        this.enableDebugDumpMessagesDetails = enableDebugDumpMessagesDetails;
    }

    public void setEnableDebuglogs(boolean enableDebuglogs) {
        this.enableDebuglogs = enableDebuglogs;
    }

}
