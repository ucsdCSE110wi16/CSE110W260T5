package net.atlassian.teammyrec.writersbloc;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.Parse;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProjectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProjectFragment newInstance(String param1, String param2) {
        AddProjectFragment fragment = new AddProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_project, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Project createProject(View v) {
        EditText projectName = (EditText) this.getActivity().findViewById(R.id.addProjectName);
        try {
            ArrayList<Project> projects = ParseController.getAllProjects();
            ArrayList<String> projectNames = new ArrayList<String>();
            for(Project p: projects) {
                System.out.println("Adding " + p.toString() + " to existing projects.");
                projectNames.add(p.toString());
            }

            if(projectNames.contains(projectName.getText().toString())) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this.getContext());
                dlgAlert.setMessage("Project '" + projectName.getText().toString() + "' already exists.");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                return;
            }
            Project project = new Project(getContext(), projectName.getText().toString(), ParseController.getCurrentUser());
            ParseController.createProject(projectName.getText().toString(),
                    ParseController.getCurrentUser());
            ParseController.createCategory("Character", projectName.getText().toString(),
                    ParseController.getCurrentUser());
            ParseController.createCategory("Location", projectName.getText().toString(),
                    ParseController.getCurrentUser());
            ParseController.createCategory("Object", projectName.getText().toString(),
                    ParseController.getCurrentUser());
            ParseController.createCategory("Other", projectName.getText().toString(),
                    ParseController.getCurrentUser());
            ParseController.createCategory("Event", projectName.getText().toString(),
                    ParseController.getCurrentUser());

            return project;
        } catch (Exception e){
            Logger.getLogger("Hello World").log(Level.SEVERE, "Failed to create project" + e);
            return null;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
