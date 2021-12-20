import 'bootstrap/dist/css/bootstrap.css'
import './App.css';
import Header from "./Components/Header";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import { Container } from 'react-bootstrap'
import Spelers from "./Components/Speler/Spelers";
import AddSpeler from "./Components/Speler/AddSpeler";
import Speler from "./Components/Speler/Speler";
import DelSpeler from "./Components/Speler/DelSpeler";
import EditSpeler from "./Components/Speler/EditSpeler";
import Login from "./Components/Login";
import Teams from "./Components/Team/Teams";
import AddTeam from "./Components/Team/AddTeam";
import Team from "./Components/Team/Team";
import AddSpelerToTeam from "./Components/Team/AddSpelerToTeam";
import Matches from "./Components/Match/Matches";
import AddMatch from "./Components/Match/AddMatch";
import Match from "./Components/Match/Match";
import EditTeam from "./Components/Team/EditTeamnaam";
import SpelerMatchHist from "./Components/SpelerRole/SpelerMatchHist";
import SpelerRoleSpeler from "./Components/SpelerRole/SpelerRoleSpeler";
import SpelerTeams from "./Components/SpelerRole/SpelerTeams";
import SpelerTeam from "./Components/SpelerRole/SpelerTeam";
import SpelerMatch from "./Components/SpelerRole/SpelerMatch";
import Home from "./Components/Home";


function App() {
    return (
        <Router>
            <div className="App">
                <Header/>
                <Container className="main">
                    <Routes>
                        <>
                            <Route path="/Login" element={<Login/>}/>
                            <Route path="/Home" element={<Home/>}/>

                            {/*Speler Role navigation*/}
                            <Route path="/SpelerMatchHist" element={<SpelerMatchHist/>}/>
                            <Route path="/SpelerGegevens" element={<SpelerRoleSpeler/>} />
                            <Route path="/SpelerTeams" element={<SpelerTeams/>}/>
                            <Route path="/SpelerTeams/:id" element={<SpelerTeam/>}/>
                            <Route path="/SpelerMatches/:id" element={<SpelerMatch/>}/>

                            {/*Speler en Manager Role navigation*/}
                            <Route path="/EditSpeler/:id" element={<EditSpeler/>}/>

                            {/*Manager Role navigation */}
                            {/*Spelers*/}
                            <Route path="/Spelers" element={<Spelers/>}/>
                            <Route path="/AddSpeler" element={<AddSpeler/>}/>
                            <Route path="/Spelers/:id" element={<Speler/>}/>
                            <Route path="/DelSpeler/:id" element={<DelSpeler/>}/>

                            {/*Teams*/}
                            <Route path="/Teams" element={<Teams/>}/>
                            <Route path="/AddTeam" element={<AddTeam/>}/>
                            <Route path="/Teams/:id" element={<Team/>}/>
                            <Route path="/Teams/:id/AddSpeler" element={<AddSpelerToTeam/>}/>
                            <Route path="/Teams/:id/EditTeamnaam" element={<EditTeam/>}/>

                            {/*Matches*/}
                            <Route path="/Matches" element={<Matches/>}/>
                            <Route path="/AddMatch" element={<AddMatch/>}/>
                            <Route path="/Matches/:id" element={<Match/>}/>
                        </>
                    </Routes>
                </Container>
            </div>
        </Router>
    );
}

export default App;
