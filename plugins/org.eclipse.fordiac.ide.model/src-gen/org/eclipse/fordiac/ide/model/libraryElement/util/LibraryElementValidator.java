/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.util;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Function;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.LocalVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherMethod;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextFunction;
import org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.TypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;

import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

import org.eclipse.gef.commands.CommandStack;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
 * @generated
 */
public class LibraryElementValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final LibraryElementValidator INSTANCE = new LibraryElementValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.fordiac.ide.model.libraryElement"; //$NON-NLS-1$

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Validate Name' of 'IInterface Element'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int IINTERFACE_ELEMENT__VALIDATE_NAME = 1;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Validate Name' of 'INamed Element'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int INAMED_ELEMENT__VALIDATE_NAME = 2;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 2;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryElementValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return LibraryElementPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case LibraryElementPackage.ADAPTER_CONNECTION:
				return validateAdapterConnection((AdapterConnection)value, diagnostics, context);
			case LibraryElementPackage.ADAPTER_DECLARATION:
				return validateAdapterDeclaration((AdapterDeclaration)value, diagnostics, context);
			case LibraryElementPackage.ADAPTER_FB:
				return validateAdapterFB((AdapterFB)value, diagnostics, context);
			case LibraryElementPackage.ADAPTER_FB_TYPE:
				return validateAdapterFBType((AdapterFBType)value, diagnostics, context);
			case LibraryElementPackage.ADAPTER_TYPE:
				return validateAdapterType((AdapterType)value, diagnostics, context);
			case LibraryElementPackage.ALGORITHM:
				return validateAlgorithm((Algorithm)value, diagnostics, context);
			case LibraryElementPackage.APPLICATION:
				return validateApplication((Application)value, diagnostics, context);
			case LibraryElementPackage.ARRAY_SIZE:
				return validateArraySize((ArraySize)value, diagnostics, context);
			case LibraryElementPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION:
				return validateAttributeDeclaration((AttributeDeclaration)value, diagnostics, context);
			case LibraryElementPackage.BASE_FB_TYPE:
				return validateBaseFBType((BaseFBType)value, diagnostics, context);
			case LibraryElementPackage.BASIC_FB_TYPE:
				return validateBasicFBType((BasicFBType)value, diagnostics, context);
			case LibraryElementPackage.AUTOMATION_SYSTEM:
				return validateAutomationSystem((AutomationSystem)value, diagnostics, context);
			case LibraryElementPackage.CFB_INSTANCE:
				return validateCFBInstance((CFBInstance)value, diagnostics, context);
			case LibraryElementPackage.COLOR:
				return validateColor((Color)value, diagnostics, context);
			case LibraryElementPackage.COLORIZABLE_ELEMENT:
				return validateColorizableElement((ColorizableElement)value, diagnostics, context);
			case LibraryElementPackage.COMMENT:
				return validateComment((Comment)value, diagnostics, context);
			case LibraryElementPackage.COMMUNICATION_CHANNEL:
				return validateCommunicationChannel((CommunicationChannel)value, diagnostics, context);
			case LibraryElementPackage.COMMUNICATION_CONFIGURATION:
				return validateCommunicationConfiguration((CommunicationConfiguration)value, diagnostics, context);
			case LibraryElementPackage.COMMUNICATION_MAPPING_TARGET:
				return validateCommunicationMappingTarget((CommunicationMappingTarget)value, diagnostics, context);
			case LibraryElementPackage.COMPILER:
				return validateCompiler((org.eclipse.fordiac.ide.model.libraryElement.Compiler)value, diagnostics, context);
			case LibraryElementPackage.COMPILER_INFO:
				return validateCompilerInfo((CompilerInfo)value, diagnostics, context);
			case LibraryElementPackage.COMPOSITE_FB_TYPE:
				return validateCompositeFBType((CompositeFBType)value, diagnostics, context);
			case LibraryElementPackage.CONFIGURABLE_OBJECT:
				return validateConfigurableObject((ConfigurableObject)value, diagnostics, context);
			case LibraryElementPackage.CONNECTION:
				return validateConnection((Connection)value, diagnostics, context);
			case LibraryElementPackage.CONNECTION_ROUTING_DATA:
				return validateConnectionRoutingData((ConnectionRoutingData)value, diagnostics, context);
			case LibraryElementPackage.DATA_CONNECTION:
				return validateDataConnection((DataConnection)value, diagnostics, context);
			case LibraryElementPackage.DEMULTIPLEXER:
				return validateDemultiplexer((Demultiplexer)value, diagnostics, context);
			case LibraryElementPackage.DEVICE:
				return validateDevice((Device)value, diagnostics, context);
			case LibraryElementPackage.DEVICE_TYPE:
				return validateDeviceType((DeviceType)value, diagnostics, context);
			case LibraryElementPackage.EC_ACTION:
				return validateECAction((ECAction)value, diagnostics, context);
			case LibraryElementPackage.ECC:
				return validateECC((ECC)value, diagnostics, context);
			case LibraryElementPackage.EC_STATE:
				return validateECState((ECState)value, diagnostics, context);
			case LibraryElementPackage.EC_TRANSITION:
				return validateECTransition((ECTransition)value, diagnostics, context);
			case LibraryElementPackage.ERROR_MARKER_DATA_TYPE:
				return validateErrorMarkerDataType((ErrorMarkerDataType)value, diagnostics, context);
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT:
				return validateErrorMarkerFBNElement((ErrorMarkerFBNElement)value, diagnostics, context);
			case LibraryElementPackage.ERROR_MARKER_INTERFACE:
				return validateErrorMarkerInterface((ErrorMarkerInterface)value, diagnostics, context);
			case LibraryElementPackage.ERROR_MARKER_REF:
				return validateErrorMarkerRef((ErrorMarkerRef)value, diagnostics, context);
			case LibraryElementPackage.EVENT:
				return validateEvent((Event)value, diagnostics, context);
			case LibraryElementPackage.EVENT_CONNECTION:
				return validateEventConnection((EventConnection)value, diagnostics, context);
			case LibraryElementPackage.FB:
				return validateFB((FB)value, diagnostics, context);
			case LibraryElementPackage.FB_NETWORK:
				return validateFBNetwork((FBNetwork)value, diagnostics, context);
			case LibraryElementPackage.FB_NETWORK_ELEMENT:
				return validateFBNetworkElement((FBNetworkElement)value, diagnostics, context);
			case LibraryElementPackage.FB_TYPE:
				return validateFBType((FBType)value, diagnostics, context);
			case LibraryElementPackage.FUNCTION:
				return validateFunction((Function)value, diagnostics, context);
			case LibraryElementPackage.FUNCTION_BODY:
				return validateFunctionBody((FunctionBody)value, diagnostics, context);
			case LibraryElementPackage.FUNCTION_FB_TYPE:
				return validateFunctionFBType((FunctionFBType)value, diagnostics, context);
			case LibraryElementPackage.GLOBAL_CONSTANTS:
				return validateGlobalConstants((GlobalConstants)value, diagnostics, context);
			case LibraryElementPackage.GROUP:
				return validateGroup((Group)value, diagnostics, context);
			case LibraryElementPackage.HIDDEN_ELEMENT:
				return validateHiddenElement((HiddenElement)value, diagnostics, context);
			case LibraryElementPackage.ICALLABLE:
				return validateICallable((ICallable)value, diagnostics, context);
			case LibraryElementPackage.IDENTIFICATION:
				return validateIdentification((Identification)value, diagnostics, context);
			case LibraryElementPackage.IINTERFACE_ELEMENT:
				return validateIInterfaceElement((IInterfaceElement)value, diagnostics, context);
			case LibraryElementPackage.IMPORT:
				return validateImport((Import)value, diagnostics, context);
			case LibraryElementPackage.INAMED_ELEMENT:
				return validateINamedElement((INamedElement)value, diagnostics, context);
			case LibraryElementPackage.INPUT_PRIMITIVE:
				return validateInputPrimitive((InputPrimitive)value, diagnostics, context);
			case LibraryElementPackage.INTERFACE_LIST:
				return validateInterfaceList((InterfaceList)value, diagnostics, context);
			case LibraryElementPackage.IVAR_ELEMENT:
				return validateIVarElement((IVarElement)value, diagnostics, context);
			case LibraryElementPackage.LIBRARY_ELEMENT:
				return validateLibraryElement((LibraryElement)value, diagnostics, context);
			case LibraryElementPackage.LINK:
				return validateLink((Link)value, diagnostics, context);
			case LibraryElementPackage.LOCAL_VARIABLE:
				return validateLocalVariable((LocalVariable)value, diagnostics, context);
			case LibraryElementPackage.MAPPING:
				return validateMapping((Mapping)value, diagnostics, context);
			case LibraryElementPackage.MAPPING_TARGET:
				return validateMappingTarget((MappingTarget)value, diagnostics, context);
			case LibraryElementPackage.METHOD:
				return validateMethod((Method)value, diagnostics, context);
			case LibraryElementPackage.MULTIPLEXER:
				return validateMultiplexer((Multiplexer)value, diagnostics, context);
			case LibraryElementPackage.ORIGINAL_SOURCE:
				return validateOriginalSource((OriginalSource)value, diagnostics, context);
			case LibraryElementPackage.OTHER_ALGORITHM:
				return validateOtherAlgorithm((OtherAlgorithm)value, diagnostics, context);
			case LibraryElementPackage.OTHER_METHOD:
				return validateOtherMethod((OtherMethod)value, diagnostics, context);
			case LibraryElementPackage.OUTPUT_PRIMITIVE:
				return validateOutputPrimitive((OutputPrimitive)value, diagnostics, context);
			case LibraryElementPackage.POSITION:
				return validatePosition((Position)value, diagnostics, context);
			case LibraryElementPackage.POSITIONABLE_ELEMENT:
				return validatePositionableElement((PositionableElement)value, diagnostics, context);
			case LibraryElementPackage.PRIMITIVE:
				return validatePrimitive((Primitive)value, diagnostics, context);
			case LibraryElementPackage.RESOURCE:
				return validateResource((Resource)value, diagnostics, context);
			case LibraryElementPackage.RESOURCE_TYPE_NAME:
				return validateResourceTypeName((ResourceTypeName)value, diagnostics, context);
			case LibraryElementPackage.RESOURCE_TYPE:
				return validateResourceType((ResourceType)value, diagnostics, context);
			case LibraryElementPackage.RESOURCE_TYPE_FB:
				return validateResourceTypeFB((ResourceTypeFB)value, diagnostics, context);
			case LibraryElementPackage.SEGMENT:
				return validateSegment((Segment)value, diagnostics, context);
			case LibraryElementPackage.SEGMENT_TYPE:
				return validateSegmentType((SegmentType)value, diagnostics, context);
			case LibraryElementPackage.SERVICE:
				return validateService((Service)value, diagnostics, context);
			case LibraryElementPackage.SERVICE_SEQUENCE:
				return validateServiceSequence((ServiceSequence)value, diagnostics, context);
			case LibraryElementPackage.SERVICE_TRANSACTION:
				return validateServiceTransaction((ServiceTransaction)value, diagnostics, context);
			case LibraryElementPackage.SERVICE_INTERFACE:
				return validateServiceInterface((ServiceInterface)value, diagnostics, context);
			case LibraryElementPackage.SERVICE_INTERFACE_FB_TYPE:
				return validateServiceInterfaceFBType((ServiceInterfaceFBType)value, diagnostics, context);
			case LibraryElementPackage.SIMPLE_FB_TYPE:
				return validateSimpleFBType((SimpleFBType)value, diagnostics, context);
			case LibraryElementPackage.ST_ALGORITHM:
				return validateSTAlgorithm((STAlgorithm)value, diagnostics, context);
			case LibraryElementPackage.ST_FUNCTION:
				return validateSTFunction((STFunction)value, diagnostics, context);
			case LibraryElementPackage.ST_FUNCTION_BODY:
				return validateSTFunctionBody((STFunctionBody)value, diagnostics, context);
			case LibraryElementPackage.ST_METHOD:
				return validateSTMethod((STMethod)value, diagnostics, context);
			case LibraryElementPackage.SUB_APP:
				return validateSubApp((SubApp)value, diagnostics, context);
			case LibraryElementPackage.STRUCT_MANIPULATOR:
				return validateStructManipulator((StructManipulator)value, diagnostics, context);
			case LibraryElementPackage.SUB_APP_TYPE:
				return validateSubAppType((SubAppType)value, diagnostics, context);
			case LibraryElementPackage.SYSTEM_CONFIGURATION:
				return validateSystemConfiguration((SystemConfiguration)value, diagnostics, context);
			case LibraryElementPackage.TEXT_ALGORITHM:
				return validateTextAlgorithm((TextAlgorithm)value, diagnostics, context);
			case LibraryElementPackage.TEXT_FUNCTION:
				return validateTextFunction((TextFunction)value, diagnostics, context);
			case LibraryElementPackage.TEXT_FUNCTION_BODY:
				return validateTextFunctionBody((TextFunctionBody)value, diagnostics, context);
			case LibraryElementPackage.TEXT_METHOD:
				return validateTextMethod((TextMethod)value, diagnostics, context);
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT:
				return validateTypedConfigureableObject((TypedConfigureableObject)value, diagnostics, context);
			case LibraryElementPackage.TYPED_ELEMENT:
				return validateTypedElement((TypedElement)value, diagnostics, context);
			case LibraryElementPackage.VALUE:
				return validateValue((Value)value, diagnostics, context);
			case LibraryElementPackage.VAR_DECLARATION:
				return validateVarDeclaration((VarDeclaration)value, diagnostics, context);
			case LibraryElementPackage.VERSION_INFO:
				return validateVersionInfo((VersionInfo)value, diagnostics, context);
			case LibraryElementPackage.WITH:
				return validateWith((With)value, diagnostics, context);
			case LibraryElementPackage.LANGUAGE:
				return validateLanguage((Language)value, diagnostics, context);
			case LibraryElementPackage.COMMAND_STACK:
				return validateCommandStack((CommandStack)value, diagnostics, context);
			case LibraryElementPackage.IFILE:
				return validateIFile((IFile)value, diagnostics, context);
			case LibraryElementPackage.IPROJECT:
				return validateIProject((IProject)value, diagnostics, context);
			case LibraryElementPackage.POINT:
				return validatePoint((Point)value, diagnostics, context);
			case LibraryElementPackage.TYPE_ENTRY:
				return validateTypeEntry((TypeEntry)value, diagnostics, context);
			case LibraryElementPackage.TYPE_LIBRARY:
				return validateTypeLibrary((TypeLibrary)value, diagnostics, context);
			case LibraryElementPackage.VAR_DECL_LIST:
				return validateVarDeclList((List<VarDeclaration>)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdapterConnection(AdapterConnection adapterConnection, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(adapterConnection, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdapterDeclaration(AdapterDeclaration adapterDeclaration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(adapterDeclaration, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(adapterDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validateIInterfaceElement_validateName(adapterDeclaration, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdapterFB(AdapterFB adapterFB, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(adapterFB, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(adapterFB, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(adapterFB, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdapterFBType(AdapterFBType adapterFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(adapterFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(adapterFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(adapterFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdapterType(AdapterType adapterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(adapterType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(adapterType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(adapterType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlgorithm(Algorithm algorithm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(algorithm, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(algorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(algorithm, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateApplication(Application application, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(application, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(application, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(application, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(application, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArraySize(ArraySize arraySize, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(arraySize, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(attribute, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(attribute, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeDeclaration(AttributeDeclaration attributeDeclaration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(attributeDeclaration, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attributeDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(attributeDeclaration, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBaseFBType(BaseFBType baseFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(baseFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(baseFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(baseFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBasicFBType(BasicFBType basicFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(basicFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(basicFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(basicFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAutomationSystem(AutomationSystem automationSystem, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(automationSystem, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(automationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(automationSystem, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCFBInstance(CFBInstance cfbInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(cfbInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(cfbInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(cfbInstance, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateColor(Color color, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(color, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateColorizableElement(ColorizableElement colorizableElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(colorizableElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComment(Comment comment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(comment, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(comment, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(comment, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationChannel(CommunicationChannel communicationChannel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(communicationChannel, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(communicationChannel, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(communicationChannel, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationConfiguration(CommunicationConfiguration communicationConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(communicationConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommunicationMappingTarget(CommunicationMappingTarget communicationMappingTarget, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(communicationMappingTarget, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(communicationMappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(communicationMappingTarget, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompiler(org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(compiler, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompilerInfo(CompilerInfo compilerInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(compilerInfo, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompositeFBType(CompositeFBType compositeFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(compositeFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(compositeFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(compositeFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConfigurableObject(ConfigurableObject configurableObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(configurableObject, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnection(Connection connection, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(connection, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnectionRoutingData(ConnectionRoutingData connectionRoutingData, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(connectionRoutingData, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDataConnection(DataConnection dataConnection, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dataConnection, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDemultiplexer(Demultiplexer demultiplexer, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(demultiplexer, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(demultiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(demultiplexer, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDevice(Device device, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(device, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(device, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(device, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(device, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDeviceType(DeviceType deviceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(deviceType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(deviceType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(deviceType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateECAction(ECAction ecAction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(ecAction, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateECC(ECC ecc, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(ecc, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateECState(ECState ecState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ecState, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(ecState, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(ecState, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateECTransition(ECTransition ecTransition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(ecTransition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateErrorMarkerDataType(ErrorMarkerDataType errorMarkerDataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(errorMarkerDataType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(errorMarkerDataType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(errorMarkerDataType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateErrorMarkerFBNElement(ErrorMarkerFBNElement errorMarkerFBNElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(errorMarkerFBNElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(errorMarkerFBNElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(errorMarkerFBNElement, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateErrorMarkerInterface(ErrorMarkerInterface errorMarkerInterface, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(errorMarkerInterface, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(errorMarkerInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validateIInterfaceElement_validateName(errorMarkerInterface, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateErrorMarkerRef(ErrorMarkerRef errorMarkerRef, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(errorMarkerRef, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEvent(Event event, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(event, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(event, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(event, diagnostics, context);
		if (result || diagnostics != null) result &= validateIInterfaceElement_validateName(event, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEventConnection(EventConnection eventConnection, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(eventConnection, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFB(FB fb, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(fb, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(fb, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(fb, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFBNetwork(FBNetwork fbNetwork, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(fbNetwork, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFBNetworkElement(FBNetworkElement fbNetworkElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(fbNetworkElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(fbNetworkElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(fbNetworkElement, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFBType(FBType fbType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(fbType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(fbType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(fbType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFunction(Function function, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(function, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(function, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(function, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(function, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFunctionBody(FunctionBody functionBody, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(functionBody, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFunctionFBType(FunctionFBType functionFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(functionFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(functionFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(functionFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGlobalConstants(GlobalConstants globalConstants, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(globalConstants, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(globalConstants, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(globalConstants, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGroup(Group group, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(group, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(group, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(group, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(group, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHiddenElement(HiddenElement hiddenElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(hiddenElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateICallable(ICallable iCallable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(iCallable, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(iCallable, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(iCallable, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdentification(Identification identification, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(identification, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIInterfaceElement(IInterfaceElement iInterfaceElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(iInterfaceElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(iInterfaceElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateIInterfaceElement_validateName(iInterfaceElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the validateName constraint of '<em>IInterface Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIInterfaceElement_validateName(IInterfaceElement iInterfaceElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return iInterfaceElement.validateName(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImport(Import import_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(import_, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateINamedElement(INamedElement iNamedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(iNamedElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(iNamedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(iNamedElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the validateName constraint of '<em>INamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateINamedElement_validateName(INamedElement iNamedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return iNamedElement.validateName(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInputPrimitive(InputPrimitive inputPrimitive, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(inputPrimitive, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInterfaceList(InterfaceList interfaceList, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(interfaceList, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIVarElement(IVarElement iVarElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(iVarElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLibraryElement(LibraryElement libraryElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(libraryElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(libraryElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(libraryElement, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLink(Link link, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(link, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(link, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(link, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(link, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocalVariable(LocalVariable localVariable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(localVariable, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(localVariable, diagnostics, context);
		if (result || diagnostics != null) result &= validateIInterfaceElement_validateName(localVariable, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapping(Mapping mapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(mapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMappingTarget(MappingTarget mappingTarget, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(mappingTarget, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(mappingTarget, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(mappingTarget, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMethod(Method method, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(method, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(method, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(method, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(method, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMultiplexer(Multiplexer multiplexer, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(multiplexer, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(multiplexer, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(multiplexer, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOriginalSource(OriginalSource originalSource, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(originalSource, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOtherAlgorithm(OtherAlgorithm otherAlgorithm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(otherAlgorithm, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(otherAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(otherAlgorithm, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOtherMethod(OtherMethod otherMethod, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(otherMethod, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(otherMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(otherMethod, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOutputPrimitive(OutputPrimitive outputPrimitive, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(outputPrimitive, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePosition(Position position, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(position, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePositionableElement(PositionableElement positionableElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(positionableElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePrimitive(Primitive primitive, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(primitive, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResource(Resource resource, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(resource, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(resource, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(resource, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceTypeName(ResourceTypeName resourceTypeName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(resourceTypeName, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceType(ResourceType resourceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(resourceType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(resourceType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(resourceType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceTypeFB(ResourceTypeFB resourceTypeFB, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(resourceTypeFB, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(resourceTypeFB, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(resourceTypeFB, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSegment(Segment segment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(segment, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(segment, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(segment, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSegmentType(SegmentType segmentType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(segmentType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(segmentType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(segmentType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateService(Service service, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(service, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceSequence(ServiceSequence serviceSequence, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(serviceSequence, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(serviceSequence, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(serviceSequence, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceTransaction(ServiceTransaction serviceTransaction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(serviceTransaction, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceInterface(ServiceInterface serviceInterface, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(serviceInterface, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(serviceInterface, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(serviceInterface, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceInterfaceFBType(ServiceInterfaceFBType serviceInterfaceFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(serviceInterfaceFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(serviceInterfaceFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(serviceInterfaceFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSimpleFBType(SimpleFBType simpleFBType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(simpleFBType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(simpleFBType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(simpleFBType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSTAlgorithm(STAlgorithm stAlgorithm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(stAlgorithm, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(stAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(stAlgorithm, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSTFunction(STFunction stFunction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(stFunction, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(stFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(stFunction, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSTFunctionBody(STFunctionBody stFunctionBody, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(stFunctionBody, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSTMethod(STMethod stMethod, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(stMethod, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(stMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(stMethod, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSubApp(SubApp subApp, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(subApp, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(subApp, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(subApp, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStructManipulator(StructManipulator structManipulator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(structManipulator, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(structManipulator, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(structManipulator, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSubAppType(SubAppType subAppType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(subAppType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(subAppType, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(subAppType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSystemConfiguration(SystemConfiguration systemConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(systemConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTextAlgorithm(TextAlgorithm textAlgorithm, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(textAlgorithm, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(textAlgorithm, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(textAlgorithm, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTextFunction(TextFunction textFunction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(textFunction, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(textFunction, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(textFunction, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTextFunctionBody(TextFunctionBody textFunctionBody, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(textFunctionBody, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTextMethod(TextMethod textMethod, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(textMethod, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(textMethod, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(textMethod, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypedConfigureableObject(TypedConfigureableObject typedConfigureableObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(typedConfigureableObject, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(typedConfigureableObject, diagnostics, context);
		if (result || diagnostics != null) result &= validateINamedElement_validateName(typedConfigureableObject, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypedElement(TypedElement typedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(typedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateValue(Value value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(value, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVarDeclaration(VarDeclaration varDeclaration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(varDeclaration, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(varDeclaration, diagnostics, context);
		if (result || diagnostics != null) result &= validateIInterfaceElement_validateName(varDeclaration, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionInfo(VersionInfo versionInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(versionInfo, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWith(With with, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(with, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLanguage(Language language, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCommandStack(CommandStack commandStack, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIFile(IFile iFile, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIProject(IProject iProject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePoint(Point point, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeEntry(TypeEntry typeEntry, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeLibrary(TypeLibrary typeLibrary, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVarDeclList(List<VarDeclaration> varDeclList, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //LibraryElementValidator