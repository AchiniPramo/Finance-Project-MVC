package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.model.tm.CollateralTm;
import lk.ijse.Micro_Finance_Management_System.repo.CollateralRepository;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.sql.SQLException;
import java.util.List;

public class CollateralFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCollateralId;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CollateralTm> tblCollateral;

    private static CollateralFormController controller;
    public CollateralFormController(){
        controller = this;
    }
    public static CollateralFormController getInstance(){
        return controller;
    }

    @FXML
    void btnCollateralManageOnAction(ActionEvent event) {
        Navigation.navigateToManageCollateral();
    }

    public void initialize() {
        setCellValueFactory();
        loadAllCollateral();
    }

    private void loadAllCollateral() {
        var model = new CollateralRepository();

        ObservableList<CollateralTm> tmList = FXCollections.observableArrayList();

        try {
            List<CollateralTm> collateralList = model.getAllCollateral();

            for (CollateralTm collateral : collateralList) {
                tmList.add(new CollateralTm(
                        collateral.getCollateralId(),
                        collateral.getName(),
                        collateral.getAddress(),
                        collateral.getPhoneNumber()

                ));
            }
            tblCollateral.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colCollateralId.setCellValueFactory(new PropertyValueFactory<>("collateralId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
}
