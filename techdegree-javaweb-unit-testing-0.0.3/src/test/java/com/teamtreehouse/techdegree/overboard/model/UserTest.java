package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Rumy on 3/12/2017.
 */
public class UserTest {
    private Board board;
    private User alice;
    private User bob;
    private User charles;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown=ExpectedException.none();


    @Before
    public void setUp() throws Exception {

        Board board = new Board("Java");

        alice = board.createUser("alice");
        bob = board.createUser("bob");
        charles = board.createUser("charles");

    }

    @Test
    public void QuestionerReputationUpFiveWhenUpvoted() throws Exception {
        int rep= alice.getReputation();
        question = alice.askQuestion("What is a String?");

        bob.upVote(question);

        assertEquals(rep+5,alice.getReputation());

    }

    @Test
    public void AnswererReputationUpTenWhenUpvoted() throws Exception {
        int rep= bob.getReputation();
        question = alice.askQuestion("What is a String?");
        answer = bob.answerQuestion(question, "It is a series of characters, strung together...");

        alice.upVote(answer);

        assertEquals(rep+10,bob.getReputation());

    }

    @Test
    public void AnswererReputationUp15WhenAnswerAccepted() throws Exception {
        int rep= bob.getReputation();
        question = alice.askQuestion("What is a String?");
        answer = bob.answerQuestion(question, "It is a series of characters, strung together...");

        alice.acceptAnswer(answer);

        assertEquals(rep+15,bob.getReputation());

    }



    @Test
    public void NoVotingForYourOwnQuestionsAllowed() throws Exception {

        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        question = alice.askQuestion("What is a String?");

        alice.upVote(question);

    }

    @Test
    public void NoVotingForYourOwnAnswersAllowed() throws Exception {

        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        question = alice.askQuestion("What is a String?");
        answer = bob.answerQuestion(question, "It is a series of characters, strung together...");

        bob.upVote(answer);

    }

    @Test
    public void OnlyOriginalQuestionerAcceptsAnswerAllowed() throws Exception {

        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only alice can accept this answer as it is their question");

        question = alice.askQuestion("What is a String?");
        answer = bob.answerQuestion(question, "It is a series of characters, strung together...");

        charles.acceptAnswer(answer);
    }

    @Test
    public void AnswererReputationDown1WhenDownvoted() throws Exception {
        int rep= bob.getReputation();
        question = alice.askQuestion("What is a String?");
        answer = bob.answerQuestion(question, "It is a series of characters, strung together...");

        alice.downVote(answer);

        assertEquals(rep-1,bob.getReputation());

    }

}