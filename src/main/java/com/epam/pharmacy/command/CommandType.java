package com.epam.pharmacy.command;

import com.epam.pharmacy.command.impl.*;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    REDIRECT_TO_REGISTRATION {
        {
            this.command = new RedirectToRegistrationCommand();
        }
    },
    REDIRECT_TO_LOGIN {
        {
            this.command = new RedirectToLoginCommand();
        }
    },
    CHANGE_PASSWORD {
        {
            this.command = new ChangePasswordCommand();
        }
    },
    PAGINATION {
        {
            this.command = new PaginationCommand();
        }
    },

    REDIRECT_TO_DRUG_TABLE {
        {
            this.command = new RedirectToDrugTableCommand();
        }
    },
    REDIRECT_TO_MAIN {
        {
            this.command = new RedirectToMainCommand();
        }
    },
    REDIRECT_TO_EDIT_DRUG {
        {
            this.command = new RedirectToEditDrugCommand();
        }
    },
    REDIRECT_TO_START {
        {
            this.command = new RedirectToStartCommand();
        }
    },
    EDIT_DRUG {
        {
            this.command = new EditDrugCommand();
        }
    },
    REDIRECT_TO_ADD_DRUG {
        {
            this.command = new RedirectToAddDrugCommand();
        }
    },
    ADD_DRUG {
        {
            this.command = new AddDrugCommand();
        }
    },
    CHANGE_LOCALE {
        {
            this.command = new ChangeLocalCommand();
        }
    },
    REDIRECT_TO_CHANGE_PASSWORD {
        {
            this.command = new RedirectToChangePassword();
        }
    },
    REDIRECT_TO_CUSTOMER_PRESCRIPTION_TABLE {
        {
            this.command = new RedirectToCustomerPrescriptionTableCommand();
        }
    },
    PRESCRIPTION_RENEWAL {
        {
            this.command = new PrescriptionRenewalCommand();
        }
    },
    REDIRECT_TO_ADD_PRESCRIPTION_ORDER {
        {
            this.command = new RedirectToAddPrescriptionOrder();
        }
    },
    ADD_PRESCRIPTION_ORDER {
        {
            this.command = new AddPrescriptionOrderCommand();
        }
    },
    CODE_CONFIRMATION {
        {
            this.command = new CodeConfirmationCommand();
        }
    },
    REDIRECT_TO_CODE_CONFIRMATION {
        {
            this.command = new RedirectToCodeConfirmation();
        }
    },
    REDIRECT_TO_PRESCRIPTION_ORDER_TABLE {
        {
            this.command = new RedirectToPrescriptionOrderTable();
        }
    },
    REDIRECT_TO_PRESCRIPTION_ORDER {
        {
            this.command = new RedirectToPrescriptionOrder();
        }
    },
    PRESCRIPTION_ORDER_CONFIRMATION {
        {
            this.command = new PrescriptionOrderConfirmationCommand();
        }
    },
    REFUSAL_PRESCRIPTION_ORDER {
        {
            this.command = new RefusalPrescriptionOrderCommand();
        }
    },
    REDIRECT_TO_DRUG_ORDER_FORM {
        {
            this.command = new RedirectToDrugOrderForm();
        }
    },
    DRUG_ORDER {
        {
            this.command = new DrugOrderCommand();
        }
    },
    DELETE_DRUG {
        {
            this.command = new DeleteDrugCommand();
        }
    },
    DELETE_DRUG_PICTURE {
        {
            this.command = new DeleteDrugPictureCommand();
        }
    },
    REDIRECT_TO_CUSTOMER_ORDER_TABLE {
        {
            this.command = new RedirectToCustomerOrderTable();
        }
    },
    REDIRECT_TO_PHARMACIST_DRUG_ORDER_TABLE {
        {
            this.command = new RedirectToPharmacistDrugOrderTable();
        }
    },
    CONFIRM_DRUG_ORDER {
        {
            this.command = new ConfirmDrugOrder();
        }
    },
    REFUSE_DRUG_ORDER {
        {
            this.command = new RefuseDrugOrder();
        }
    },
    REDIRECT_TO_ACCOUNT_REPLENISHMENT {
        {
            this.command = new RedirectToAccountReplenishment();
        }
    },
    ACCOUNT_REPLENISHMENT {
        {
            this.command = new AccountReplenishment();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
    }
