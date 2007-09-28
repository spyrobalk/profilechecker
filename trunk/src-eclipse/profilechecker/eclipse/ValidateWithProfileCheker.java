package profilechecker.eclipse;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Model;
import profilechecker.model.ValidationException;
import profilechecker.model.ValidationException.Level;

public class ValidateWithProfileCheker implements IObjectActionDelegate {

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
						ProfileCheckerController controller = new ProfileCheckerController();
						Model model = new Model();
						controller.parser( model, file.getLocation().toFile() );
						controller.validate( model );
						for ( ValidationException ve : model.getValidationExceptions() ) {
							String message = ve.getMessage();
							int lineNumber = ve.getLine();
							int severity = (Level.warning.equals( ve.getLevel() )	? IMarker.SEVERITY_WARNING
																					: IMarker.SEVERITY_ERROR);

							IMarker marker = file.createMarker( MARKER_TYPE );
							marker.setAttribute( IMarker.MESSAGE, message );
							marker.setAttribute( IMarker.SEVERITY, severity );

							if ( lineNumber == -1 ) {
								lineNumber = 1;
							}

							marker.setAttribute( IMarker.LINE_NUMBER, lineNumber );

						}
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
