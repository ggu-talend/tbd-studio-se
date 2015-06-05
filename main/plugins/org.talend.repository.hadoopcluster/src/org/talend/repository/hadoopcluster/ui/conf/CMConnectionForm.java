// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.ui.conf;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.repository.hadoopcluster.conf.model.HadoopConfsConnection;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.i18n.Messages;

/**
 * created by ycbai on 2015年6月4日 Detailled comment
 *
 */
public class CMConnectionForm extends AbstractConnectionForm {

    public CMConnectionForm(Composite parent, int style) {
        super(parent, style);
    }

    @Override
    protected void createControl() {
        Composite connectionGroup = createParentGroup(
                Messages.getString("HadoopImportRemoteOptionPage.group.connection.cm"), this, 2); //$NON-NLS-1$
        connURLText = new LabelledText(connectionGroup, Messages.getString("HadoopImportRemoteOptionPage.text.connURL.cm"), 1); //$NON-NLS-1$
        usernameText = new LabelledText(connectionGroup, Messages.getString("HadoopImportRemoteOptionPage.text.username"), 1); //$NON-NLS-1$
        passwordText = new LabelledText(connectionGroup, Messages.getString("HadoopImportRemoteOptionPage.text.password"), 1); //$NON-NLS-1$
        passwordText.getTextControl().setEchoChar('*');
        connButton = new Button(connectionGroup, SWT.PUSH);
        GridData connBtnGD = new GridData(SWT.END, SWT.CENTER, false, false);
        connBtnGD.horizontalSpan = 2;
        connButton.setLayoutData(connBtnGD);
        connButton.setText(Messages.getString("HadoopImportRemoteOptionPage.button.connect")); //$NON-NLS-1$
    }

    @Override
    protected void fillDefaultValues() {
        connURLText.setText("http://localhost:7180/"); //$NON-NLS-1$
        usernameText.setText("cloudera"); //$NON-NLS-1$
        passwordText.setText("cloudera"); //$NON-NLS-1$
    }

    @Override
    protected void addListener() {
        connButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkConnection();
            }
        });
    }

    @Override
    protected HadoopConfigurationManager getHadoopConfigurationManager() {
        return HadoopConfigurationManager.CLOUDERA_MANAGER;
    }

    @Override
    protected HadoopConfsConnection getHadoopConfsConnection() {
        HadoopConfsConnection confsConnection = new HadoopConfsConnection();
        confsConnection.setConnURL(getConnURL());
        confsConnection.setUsername(getUsername());
        confsConnection.setPassword(getPassword());
        return confsConnection;
    }

}