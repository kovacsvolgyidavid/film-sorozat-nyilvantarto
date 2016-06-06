package xyz.codingmentor.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.service.ActorFacade;
import xyz.codingmentor.service.SeriesFacade;

@Named
@RequestScoped
public class Queries {
    private static final String NO_SUCH_SERIES = "There is no such series.";
    private static final String ERROR = "Error!";
    
    private Date date;
    private int numberOfEpisodes;
    private List<Actor> actors;
    private List<Series> series;
    private List<String> usersWithMostComments;

    @Inject
    private ActorFacade actorFacade;

    @Inject
    private SeriesFacade seriesFacade;
    private static final Logger LOG = Logger.getLogger(Queries.class.getName());

    @PostConstruct
    public void init() {
        actors = new ArrayList<>();
        series = new ArrayList<>();
        usersWithMostComments = new ArrayList<>();    
    }

    public String goToSeriesPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String id = params.get("seriesid");  
        String s    = "seriesEdit.xhtml/?seriesid=" + id + ";faces-redirect=true";
        LOG.info(s);
        return "seriesEdit.xhtml/?seriesid=" + id + ";faces-redirect=true";
    }

    public void actorsFromSeriesAfterGivenDate() {
        actors = actorFacade.actorsFromSeriesAfterGivenDate(date);
        if (actors.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no such actor.", ERROR));
        }
    }

    public void seriesByDirectorOriginalNameEqualsName() {
        series = seriesFacade.seriesByDirectorOriginalNameEqualsName();
        if (series.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, NO_SUCH_SERIES, ERROR));
        }
    }

    public void seriesWithMoreEpisode() {
        series = seriesFacade.seriesWithMoreEpisode(numberOfEpisodes);
        if (series.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, NO_SUCH_SERIES, ERROR));
        }
    }

    public void seriesCommentedAfterGivenDate() {
        series = seriesFacade.seriesCommentedAfterGivenDate(date);
        if (series.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, NO_SUCH_SERIES, ERROR));
        }
    }
    
     public void userByMostComment() {
        List <Object[]> userCommentObjects = seriesFacade.userByMostComment();
        if (userCommentObjects.isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No one commented yet.", ERROR));
        else{
            for(Object[] uco : userCommentObjects){
                usersWithMostComments.add(uco[0] + " (" + uco[1] + ")");
            }
        }         
    }   

    public void onTabChange() {
        actors.clear();
        series.clear();
        numberOfEpisodes = 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public List<String> getUsersWithMostComments() {
        return usersWithMostComments;
    }

    public void setUsersWithMostComments(List<String> usersWithMostComments) {
        this.usersWithMostComments = usersWithMostComments;
    }
}
