package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("job", jobData.findById(id));

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
                      return "new-job";
                  }

                Integer empInt = jobForm.getEmployerId();
                Integer locInt = jobForm.getLocationId();
                Integer posInt = jobForm.getPositionTypeId();
                Integer coreCompInt = jobForm.getCoreCompetencyId();

                        Employer emp = jobData.getEmployers().findById(empInt);
                         jobData.getEmployers().add(emp);

                       Location loc = jobData.getLocations().findById(locInt);
                       jobData.getLocations().add(loc);

                       PositionType posType = jobData.getPositionTypes().findById(posInt);
                       jobData.getPositionTypes().add(posType);

                       CoreCompetency coreComp = jobData.getCoreCompetencies().findById(coreCompInt);
                       jobData.getCoreCompetencies().add(coreComp);

                       Job newJob = new Job(jobForm.getName(), emp, loc, posType, coreComp);

                       jobData.add(newJob);

                      return "redirect:/job?id=" + Integer.toString(newJob.getId());

    }

}

