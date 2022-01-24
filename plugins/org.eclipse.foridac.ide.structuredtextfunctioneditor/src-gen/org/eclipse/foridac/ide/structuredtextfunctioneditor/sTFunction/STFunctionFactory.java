/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.foridac.ide.structuredtextfunctioneditor.sTFunction;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.foridac.ide.structuredtextfunctioneditor.sTFunction.STFunctionPackage
 * @generated
 */
public interface STFunctionFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  STFunctionFactory eINSTANCE = org.eclipse.foridac.ide.structuredtextfunctioneditor.sTFunction.impl.STFunctionFactoryImpl.init();

  /**
   * Returns a new object of class '<em>ST Function</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>ST Function</em>'.
   * @generated
   */
  STFunction createSTFunction();

  /**
   * Returns a new object of class '<em>Function Definition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Function Definition</em>'.
   * @generated
   */
  FunctionDefinition createFunctionDefinition();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  STFunctionPackage getSTFunctionPackage();

} //STFunctionFactory
