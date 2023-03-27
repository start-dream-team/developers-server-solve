package com.developers.solve.solution.service;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.dto.SolutionId;
import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.dto.SolutionResponse;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Log4j2
public class SolutionServiceImpl implements SolutionService {
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;

    @Override
    public SolutionResponse save(SolutionRequest request) {
        //given
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Solution solution = Solution.builder()
                .solver(request.getSolver())
                .problem(problem)
                .build();
        //when
        Long result = solutionRepository.save(solution).getSolutionId();

        SolutionId solutionId = SolutionId.builder()
                .solutionId(result)
                .build();

        SolutionResponse response =  SolutionResponse.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 처리 되었습니다.")
                .data(solutionId)
                .build();

        //then
        return response;
    }

}
