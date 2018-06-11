/*
    Foilen Infra Resource Email
    https://github.com/foilen/foilen-infra-resource-email
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.email;

import java.util.Arrays;
import java.util.Collections;

import com.foilen.infra.plugin.v1.core.context.CommonServicesContext;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionProvider;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionV1;
import com.foilen.infra.resource.email.editors.AttachableEmailRelayEditor;
import com.foilen.infra.resource.email.editors.EmailAccountEditor;
import com.foilen.infra.resource.email.editors.EmailDomainEditor;
import com.foilen.infra.resource.email.editors.EmailRedirectionEditor;
import com.foilen.infra.resource.email.editors.EmailRelayEditor;
import com.foilen.infra.resource.email.editors.JamesEmailServerEditor;
import com.foilen.infra.resource.email.handlers.EmailDomainEventHandler;
import com.foilen.infra.resource.email.handlers.JamesEmailServerEventHandler;
import com.foilen.infra.resource.email.resources.AttachableEmailRelay;
import com.foilen.infra.resource.email.resources.EmailAccount;
import com.foilen.infra.resource.email.resources.EmailDomain;
import com.foilen.infra.resource.email.resources.EmailRedirection;
import com.foilen.infra.resource.email.resources.EmailRelay;
import com.foilen.infra.resource.email.resources.EmailServer;
import com.foilen.infra.resource.email.resources.JamesEmailServer;

public class FoilenEmailPluginDefinitionProvider implements IPPluginDefinitionProvider {

    @Override
    public IPPluginDefinitionV1 getIPPluginDefinition() {
        IPPluginDefinitionV1 pluginDefinition = new IPPluginDefinitionV1("Foilen", "Email", "To manage emails", "1.0.0");

        pluginDefinition.addCustomResource(EmailServer.class, "Email Server", //
                Arrays.asList(EmailServer.PROPERTY_NAME), //
                Collections.emptyList());
        pluginDefinition.addCustomResource(JamesEmailServer.class, "Apache James Email Server", //
                Arrays.asList(EmailServer.PROPERTY_NAME), //
                Collections.emptyList());
        pluginDefinition.addCustomResource(EmailRelay.class, "Email Relay", //
                Arrays.asList(EmailRelay.PROPERTY_NAME), //
                Arrays.asList( //
                        EmailRelay.PROPERTY_NAME, //
                        EmailRelay.PROPERTY_HOSTNAME //
                ));
        pluginDefinition.addCustomResource(EmailDomain.class, "Email Domain", //
                Arrays.asList(EmailDomain.PROPERTY_DOMAIN_NAME), //
                Arrays.asList( //
                        EmailDomain.PROPERTY_DOMAIN_NAME, //
                        EmailDomain.PROPERTY_IMAP_DOMAIN_NAME, //
                        EmailDomain.PROPERTY_POP3_DOMAIN_NAME //
                ));
        pluginDefinition.addCustomResource(EmailRedirection.class, "Email Redirection", //
                Arrays.asList(EmailRedirection.PROPERTY_UID), //
                Arrays.asList( //
                        EmailRedirection.PROPERTY_UID, //
                        EmailRedirection.PROPERTY_ACCOUNT_NAME, //
                        EmailRedirection.PROPERTY_REDIRECT_TOS //
                ));
        pluginDefinition.addCustomResource(EmailAccount.class, "Email Account", //
                Arrays.asList(EmailAccount.PROPERTY_UID), //
                Arrays.asList( //
                        EmailAccount.PROPERTY_UID, //
                        EmailAccount.PROPERTY_ACCOUNT_NAME //
                ));
        pluginDefinition.addCustomResource(AttachableEmailRelay.class, "Attachable Local Email Server", //
                Arrays.asList(EmailServer.PROPERTY_NAME), //
                Collections.emptyList());

        // Resource editors
        pluginDefinition.addTranslations("/com/foilen/infra/resource/email/messages");
        pluginDefinition.addResourceEditor(new AttachableEmailRelayEditor(), AttachableEmailRelayEditor.EDITOR_NAME);
        pluginDefinition.addResourceEditor(new EmailAccountEditor(), EmailAccountEditor.EDITOR_NAME);
        pluginDefinition.addResourceEditor(new EmailDomainEditor(), EmailDomainEditor.EDITOR_NAME);
        pluginDefinition.addResourceEditor(new EmailRedirectionEditor(), EmailRedirectionEditor.EDITOR_NAME);
        pluginDefinition.addResourceEditor(new EmailRelayEditor(), EmailRelayEditor.EDITOR_NAME);
        pluginDefinition.addResourceEditor(new JamesEmailServerEditor(), JamesEmailServerEditor.EDITOR_NAME);

        // Updater Handler
        pluginDefinition.addUpdateHandler(new EmailDomainEventHandler());
        pluginDefinition.addUpdateHandler(new JamesEmailServerEventHandler());

        return pluginDefinition;
    }

    @Override
    public void initialize(CommonServicesContext commonServicesContext) {
    }

}
