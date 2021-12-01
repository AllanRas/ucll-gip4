import 'bootstrap/dist/css/bootstrap.css'
import {Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link} from "react-router-dom";


const Header = () => {
    return(
        /** de navigation bar  tutorial: https://www.youtube.com/watch?v=-HEjsVkfjOk **/
        <Navbar bg={"dark"} variant={"dark"}
            sticky={"top"} expand={"sm"} >
            <Navbar.Brand as={Link} to={"/"} style={{marginLeft: "1rem"}}>
                GIP 4
            </Navbar.Brand>

            <Navbar.Toggle />
            <Navbar.Collapse>
                <Nav>
                    <Nav.Link as={Link} to={"/Spelers"}>
                        Spelers
                    </Nav.Link>
                    <Nav.Link as={Link} to={"/Teams"}>
                        Teams
                    </Nav.Link>
                    <Nav.Link as={Link} to={"/Matches"}>
                        Matches
                    </Nav.Link>
                    <NavDropdown title={"exampleDropdown"} menuVariant={"dark"}>
                        <NavDropdown.Item>
                            <Nav.Link as={Link} to={"/example1"}>example1</Nav.Link>
                        </NavDropdown.Item>
                        <NavDropdown.Item>
                            <Nav.Link as={Link} to={"/example2"}>example2</Nav.Link>
                        </NavDropdown.Item>
                    </NavDropdown>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default Header;