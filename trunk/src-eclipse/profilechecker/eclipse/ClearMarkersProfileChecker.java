package profilechecker.eclipse;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ClearMarkersProfileChecker implements IObjectActionDelegate {

	private ISelection selection;

	private static final String MARKER_TYPE = "ProfileChecker.xmlProblem";

	public static final String BUILDER_ID = "ProfileChecker.sampleBuilder";
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run( IAction action ) {

		if ( selection instanceof IStructuredSelection ) {
			for ( Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext(); ) {
				Object element = it.next();
				IProject project = null;
				if ( element instanceof IFile && ((IFile) element).getName().endsWith( ".xmi" ) ) {
					IFile file = (IFile) element;
					try {
						file.deleteMarkers( MARKER_TYPE, false, IResource.DEPTH_ZERO );
					} catch ( Exception e1 ) {
					}
				}

			}
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged( IAction action, ISelection selection ) {

		this.selection = selection;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart( IAction action, IWorkbenchPart targetPart ) {

	}


}
