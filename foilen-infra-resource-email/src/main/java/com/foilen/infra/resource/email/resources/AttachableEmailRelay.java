/*
    Foilen Infra Resource Email
    https://github.com/foilen/foilen-infra-resource-email
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.email.resources;

import java.util.Optional;

import com.foilen.infra.plugin.v1.core.context.ChangesContext;
import com.foilen.infra.plugin.v1.core.context.CommonServicesContext;
import com.foilen.infra.plugin.v1.core.eventhandler.CommonMethodUpdateEventHandlerContext;
import com.foilen.infra.plugin.v1.model.base.IPApplicationDefinition;
import com.foilen.infra.plugin.v1.model.msmtp.MsmtpConfig;
import com.foilen.infra.plugin.v1.model.outputter.msmtp.MsmtpConfigOutput;
import com.foilen.infra.plugin.v1.model.resource.InfraPluginResourceCategory;
import com.foilen.infra.plugin.v1.model.resource.LinkTypeConstants;
import com.foilen.infra.resource.application.Application;
import com.foilen.infra.resource.composableapplication.AttachablePart;
import com.foilen.infra.resource.composableapplication.ComposableApplication;

/**
 * To add a local email server to a {@link ComposableApplication} that redirects all mails to the {@link EmailRelay}.
 *
 * Link to:
 * <ul>
 * <li>{@link EmailRelay}: (1) POINTS_TO - Where to send the emails</li>
 * </ul>
 */
public class AttachableEmailRelay extends AttachablePart {

    public static final String PROPERTY_NAME = "name";

    // Details
    private String name;

    @Override
    public void attachTo(CommonServicesContext services, ChangesContext changes, CommonMethodUpdateEventHandlerContext<?> context, Application application,
            IPApplicationDefinition applicationDefinition) {

        Optional<EmailRelay> emailRelayOptional = services.getResourceService().linkFindAllByFromResourceAndLinkTypeAndToResourceClass(this, LinkTypeConstants.POINTS_TO, EmailRelay.class).stream()
                .findAny();
        if (!emailRelayOptional.isPresent()) {
            return;
        }

        // Add configuration
        EmailRelay emailRelay = emailRelayOptional.get();
        MsmtpConfig msmtpConfig = new MsmtpConfig(emailRelay.getHostname(), emailRelay.getPort());
        msmtpConfig.setUsername(emailRelay.getUsername()).setPassword(emailRelay.getPassword());
        String configContent = MsmtpConfigOutput.toConfig(msmtpConfig);

        String configPath = "/_infra/emailRelay_" + emailRelay.getName();
        applicationDefinition.addAssetContent(configPath, configContent);

        // Add service
        applicationDefinition.addService("email_relay", "/usr/bin/msmtp -C " + configPath);
    }

    public String getName() {
        return name;
    }

    @Override
    public InfraPluginResourceCategory getResourceCategory() {
        return InfraPluginResourceCategory.EMAIL;
    }

    @Override
    public String getResourceDescription() {
        return "Email Domain";
    }

    @Override
    public String getResourceName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
