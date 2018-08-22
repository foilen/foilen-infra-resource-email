/*
    Foilen Infra Resource Email
    https://github.com/foilen/foilen-infra-resource-email
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.email;

import java.util.Optional;

import org.junit.Test;

import com.foilen.infra.plugin.core.system.fake.junits.AbstractIPPluginTest;
import com.foilen.infra.plugin.core.system.junits.JunitsHelper;
import com.foilen.infra.plugin.core.system.junits.ResourcesDump;
import com.foilen.infra.plugin.v1.core.context.ChangesContext;
import com.foilen.infra.plugin.v1.core.service.IPResourceService;
import com.foilen.infra.resource.application.Application;
import com.foilen.infra.resource.email.resources.EmailDomain;
import com.foilen.smalltools.tools.JsonTools;

public class JamesEmailServerTest extends AbstractIPPluginTest {

    @Test
    public void test_basic() {
        // Execute the dump
        ResourcesDump resourcesDump = JsonTools.readFromResource("JamesEmailServerTest_test_basic-import.json", ResourcesDump.class, getClass());
        JunitsHelper.dumpImport(getCommonServicesContext(), getInternalServicesContext(), resourcesDump);

        // Remove the binary files that are always changing
        getInternalServicesContext().getInternalIPResourceService().resourceFindAll().stream() //
                .filter(it -> it instanceof Application) //
                .forEach(it -> {
                    Application application = (Application) it;
                    application.getApplicationDefinition().getAssetsBundles().forEach(bundle -> {
                        bundle.getAssetsRelativePathAndBinaryContent().forEach(file -> file.setB(new byte[] {}));
                    });
                });

        // Assert
        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "JamesEmailServerTest_test_basic-state-allSameHost.json", getClass(), true);

        // Change for all different hosts
        IPResourceService resourceService = getCommonServicesContext().getResourceService();
        Optional<EmailDomain> emailDomainOptional = resourceService.resourceFind(resourceService.createResourceQuery(EmailDomain.class));
        EmailDomain emailDomain = emailDomainOptional.get();
        emailDomain.setImapDomainName("imap.example.com");
        emailDomain.setPop3DomainName("pop3.example.com");
        ChangesContext changes = new ChangesContext(resourceService);
        changes.resourceUpdate(emailDomain);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        // Remove the binary files that are always changing
        getInternalServicesContext().getInternalIPResourceService().resourceFindAll().stream() //
                .filter(it -> it instanceof Application) //
                .forEach(it -> {
                    Application application = (Application) it;
                    application.getApplicationDefinition().getAssetsBundles().forEach(bundle -> {
                        bundle.getAssetsRelativePathAndBinaryContent().forEach(file -> file.setB(new byte[] {}));
                    });
                });

        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "JamesEmailServerTest_test_basic-state-allDifferentHosts.json", getClass(), true);

    }

}
