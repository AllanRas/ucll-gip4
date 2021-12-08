import 'bootstrap/dist/css/bootstrap.css'
import './App.css';
import Header from "./Components/Header";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import { Container } from 'react-bootstrap'
import Spelers from "./Components/Spelers";
import AddSpeler from "./Components/AddSpeler";
import Speler from "./Components/Speler";
import DelSpeler from "./Components/DelSpeler";
import EditSpeler from "./Components/EditSpeler";
import Login from "./Components/Login";


function App() {

    return (
        <Router>
            <div className="App">
                <Header />
                <Container className="main">
                    <Routes>
                        <>
                            <Route path="/Spelers" element={<Spelers/>}/>
                            <Route path="/AddSpeler" element={<AddSpeler/>}/>
                            <Route path="/Spelers/:id" element={<Speler/>}/>
                            <Route path="/DelSpeler/:id" element={<DelSpeler/>}/>
                            <Route path="/EditSpeler/:id" element={<EditSpeler/>}/>
                            <Route path="/Login" element={<Login/>}/>
                        </>
                    </Routes>
                </Container>
            </div>
        </Router>
    );
}

export default App;
