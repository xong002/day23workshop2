package sg.nus.iss.day23workshop2.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.day23workshop2.model.Customer;
import sg.nus.iss.day23workshop2.model.Loan;
import sg.nus.iss.day23workshop2.model.LoanDetails;
import sg.nus.iss.day23workshop2.model.Video;
import sg.nus.iss.day23workshop2.repository.CustomerRepository;
import sg.nus.iss.day23workshop2.repository.LoanDetailsRepository;
import sg.nus.iss.day23workshop2.repository.LoanRepository;
import sg.nus.iss.day23workshop2.repository.VideoRepository;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    VideoRepository videoRepo;

    @Autowired
    LoanDetailsRepository loanDetailsRepository;

    public Boolean loanVideo(Customer customer, List<Video> videos) {
        Boolean bLoanSuccessful = false;

        List<Video> allVideos = videoRepo.findAll();

        Boolean bAvailable = true;
        for (Video video : videos) {
            List<Video> filteredVideoList = allVideos.stream().filter(v -> v.getId().equals(video.getId()))
                    .collect(Collectors.toList());
            if (!filteredVideoList.isEmpty()) {
                if (filteredVideoList.get(0).getAvailableCount() == 0) {
                    bAvailable = false;
                    // throw custom exception - no available video
                } else {
                    Video foundVideo = filteredVideoList.get(0);
                    foundVideo.setAvailableCount(foundVideo.getAvailableCount() - 1);
                    videoRepo.updateVideo(foundVideo);
                }
            }

        }

        if(bAvailable){
        //create loan record
        //create loan details
            Loan loan = new Loan();
            loan.setCustomerId(customer.getId());
            loan.setLoanDate(Date.valueOf(LocalDate.now()));

            Integer createdLoanId = loanRepo.insertLoan(loan); // can also use createLoan method

            for (Video video : videos){
                LoanDetails loanDetails = new LoanDetails();
                loanDetails.setLoanId(createdLoanId);
                loanDetails.setVideoId(video.getId());
                loanDetailsRepository.insertLoan(loanDetails);
            }

            bLoanSuccessful = true;


        }

        return bLoanSuccessful;
    }

}
