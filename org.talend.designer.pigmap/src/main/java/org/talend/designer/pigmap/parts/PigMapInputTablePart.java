// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.parts;

import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.talend.designer.gefabstractmap.figures.anchors.FilterTreeAnchor;
import org.talend.designer.gefabstractmap.figures.cells.IWidgetCell;
import org.talend.designer.gefabstractmap.part.InputTablePart;
import org.talend.designer.gefabstractmap.part.directedit.PigMapNodeCellEditorLocator;
import org.talend.designer.pigmap.figures.InputTableFigure;
import org.talend.designer.pigmap.figures.table.PigMapTableManager;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.parts.directedit.PigMapNodeDirectEditManager;
import org.talend.designer.pigmap.policy.DragAndDropEditPolicy;
import org.talend.designer.pigmap.policy.PigDirectEditPolicy;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapInputTablePart extends InputTablePart implements NodeEditPart {

    private InputTableFigure figure;

    private PigMapTableManager manager;

    private PigMapNodeDirectEditManager directEditManager;

    @Override
    protected IFigure createFigure() {
        figure = new InputTableFigure(manager);
        return figure;
    }

    @Override
    public void setModel(Object model) {
        super.setModel(model);
        manager = new PigMapTableManager((InputTable) model, this);
    }

    @Override
    public void notifyChanged(Notification notification) {
        int type = notification.getEventType();
        int featureId = notification.getFeatureID(PigmapPackage.class);
        switch (type) {
        case Notification.ADD:
        case Notification.REMOVE:
        case Notification.REMOVE_MANY:
            switch (featureId) {
            case PigmapPackage.INPUT_TABLE__NODES:
                refreshChildren();
                break;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS:
                refreshTargetConnections();
                break;
            }
            break;
        case Notification.SET:
            switch (featureId) {
            case PigmapPackage.INPUT_TABLE__NODES:
                refreshChildren();
                break;
            case PigmapPackage.INPUT_TABLE__ACTIVATE_CONDENSED_TOOL:
                getFigure().validate();
                break;
            case PigmapPackage.INPUT_TABLE__LOOKUP:
            case PigmapPackage.INPUT_TABLE__LOOKUP_MODE:
            case PigmapPackage.INPUT_TABLE__MATCHING_MODE:
            case PigmapPackage.INPUT_TABLE__PERSISTENT:
            case PigmapPackage.INPUT_TABLE__INNER_JOIN:
            case PigmapPackage.INPUT_TABLE__MINIMIZED:
            case PigmapPackage.INPUT_TABLE__EXPRESSION_FILTER:
                ((InputTableFigure) getFigure()).update(featureId);
                break;
            }
        }
    }

    @Override
    public IFigure getContentPane() {
        return ((InputTableFigure) getFigure()).getColumnContainer();
    }

    @Override
    protected List getModelChildren() {
        return ((InputTable) getModel()).getNodes();
    }

    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        super.addChildVisual(childEditPart, index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        if (connection instanceof PigMapFilterConnectionPart) {
            return new FilterTreeAnchor(getFigure(), connection, manager);
        }
        return null;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new NonResizableEditPolicy());
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new PigDirectEditPolicy());
        installEditPolicy("Drag and Drop", new DragAndDropEditPolicy());
    }

    @Override
    protected List getModelTargetConnections() {
        return ((AbstractInOutTable) getModel()).getFilterIncomingConnections();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperTablePart#highLightHeader(boolean)
     */
    @Override
    public void highLightHeader(boolean highLight) {
        ((InputTableFigure) getFigure()).highLightHeader(highLight);
    }

    @Override
    public void performRequest(Request req) {
        if (RequestConstants.REQ_DIRECT_EDIT.equals(req.getType())) {
            DirectEditRequest drequest = (DirectEditRequest) req;
            Point figureLocation = drequest.getLocation();
            IFigure findFigureAt = getFigure().findFigureAt(figureLocation.x, figureLocation.y);
            if (findFigureAt instanceof IWidgetCell) {
                directEditManager = new PigMapNodeDirectEditManager(this, new PigMapNodeCellEditorLocator((Figure) findFigureAt));
                directEditManager.show();
            }
        }
    }
}