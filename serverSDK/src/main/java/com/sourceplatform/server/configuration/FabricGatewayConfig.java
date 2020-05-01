package com.sourceplatform.server.configuration;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class FabricGatewayConfig {

    private static Log logger = LogFactory.getLog(FabricGatewayConfig.class);

    @Value("${wallet.directory}")
    private String walletDir;

    @Value("${user.directory}")
    private String userDir;

    @Value("${user.cert}")
    private String certName;

    @Value("${user.pk}")
    private String pkName;

    @Value("${org.mspid}")
    private String orgMsp;

    @Value("${network.config}")
    private String configPath;

    @Value("${channel.name}")
    private String channelName;

    private static String USERNAME = "user1";

    @Bean(name = "fabricContract")
    public Contract getContract() throws IOException, CertificateException, InvalidKeyException {
        Path walletDirectory =  Paths.get(walletDir);
        Wallet wallet = Wallets.newFileSystemWallet(walletDirectory);
        Identity id = wallet.get(USERNAME);
        if (id == null){
            wallet.put(USERNAME, getIdentity());
        }
        Path networkConfigFile = Paths.get(configPath);
        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet,USERNAME)
                .networkConfig(networkConfigFile);
        Gateway gateway = builder.connect();
        Network network = gateway.getNetwork(channelName);
        return network.getContract("process");
    }

    /**
     * read given user pem and pk signed by CA and put user into local wallet
     * @return identity created by given user pem and pk
     * @throws IOException io exception
     * @throws CertificateException certificate invalid
     * @throws InvalidKeyException key invalid
     */
    private Identity getIdentity() throws IOException, CertificateException, InvalidKeyException {
        Path userPath = Paths.get(userDir);
        Path cPath = userPath.resolve(Paths.get(
                "signcerts",
                certName
        ));
        Path pkPath = userPath.resolve(Paths.get(
                "keystore",
                pkName
        ));
        X509Certificate cert = Identities.readX509Certificate(Files.newBufferedReader(cPath));
        PrivateKey pk = Identities.readPrivateKey(Files.newBufferedReader(pkPath));
        return Identities.newX509Identity(orgMsp,cert,pk);
    }

}
